$(document).ready(function () {

    var urlAjax = $('#urlAjax').val();

    $('#buscar_producto').autocomplete({
        source: function (request, response) {
            $.ajax({
                url: urlAjax + request.term,
                dataType: 'json',
                data: {
                    term: request.term
                },
                success: function (data) {
                    response($.map(data, function (item) {
                        return {
                            value: item.id,
                            label: item.nombre,
                            precio: item.precio
                        };
                    }))
                }
            });
        },
        select: function (event, ui) {
            // $('#buscar_producto').val(ui.item.label);

            if (itemsHelper.hasProducto(ui.item.value)) {
                itemsHelper.incrementaCantidad(ui.item.value, ui.item.precio);
                return false;
            }

            var linea = $('#plantillaItemsFactura').html();

            linea = linea.replace(/{ID}/g, ui.item.value);
            linea = linea.replace(/{NOMBRE}/g, ui.item.label);
            linea = linea.replace(/{PRECIO}/g, itemsHelper.formatCurrency(ui.item.precio));

            $('#cargarItemProductos tbody').append(linea);

            itemsHelper.calcularImporte(ui.item.value, ui.item.precio, 1);

            $('#cantidad_' + ui.item.value).change(function () {
                itemsHelper.calcularImporte(ui.item.value, ui.item.precio, $(this).val());
            });

            $('#boton_eliminar_' + ui.item.value).click(function () {
                itemsHelper.eliminarLineaFactura(ui.item.value);
            });

            return false;
        }
    });
    $('form').submit(function () {
        $('#plantillaItemsFactura').remove();
        return;
    });
});

var itemsHelper = {
    calcularImporte: function (id, precio, cantidad) {
        $('#total_importe_' + id).html(this.formatCurrency(parseFloat(precio) * parseInt(cantidad)));
        this.calcularGranTotal();
    },
    hasProducto: function (id) {
        var resultado = false;

        $('input[name="item_id[]"]').each(function () {
            if (parseInt(id) === parseInt($(this).val())) {
                resultado = true;
            }
        });

        return resultado;
    },
    incrementaCantidad: function (id, precio) {
        var cantidad = $('#cantidad_' + id).val() ? parseInt($('#cantidad_' + id).val()) : 0;
        $('#cantidad_' + id).val(++cantidad);
        this.calcularImporte(id, precio, cantidad);

    },
    formatCurrency: function (valor) {
        return '$' + parseFloat(valor).toFixed(2);
    },
    formatFloat: function (currency) {
        if (currency.includes('$')) {
            return currency.split('$')[1];
        }
        return currency;
    },
    eliminarLineaFactura: function (id) {
        $('#row_' + id).remove();
        this.calcularGranTotal();
    },
    calcularGranTotal: function () {
        var total = 0;

        $('span[id^="total_importe_"]').each(function () {
            total += parseFloat(itemsHelper.formatFloat($(this).html()));
        });

        $('#gran_total').html(this.formatCurrency(total));
    }
};
