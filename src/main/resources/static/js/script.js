/* $(document).ready(function() {
    $('.eliminar').click(function() {
        return confirm('¿Estas seguro que quieres eliminar?');
    });
}); */
$(function() {
    $('.eliminar_cliente').click(function() {
        return confirm($('#i18n_eliminar_cliente').text());
    });

    $('.eliminar_factura').click(function() {
        return confirm($('#i18n_eliminar_factura').text());
    });
});
