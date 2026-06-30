package canvia.support.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Modelo (POJO) de una reserva de restful-booker.
 *
 * Sirve para DOS cosas:
 *  - Serializar: convertir este objeto Java a JSON para enviarlo en el body (POST/PUT).
 *  - Deserializar: convertir el JSON de la respuesta de vuelta a un objeto Java.
 *
 * Los nombres de los campos coinciden EXACTAMENTE con las claves del JSON de la API.
 * Incluye un patrón "builder fluido" sencillo para construir datos de prueba legibles.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Booking {

    private String firstname;
    private String lastname;
    private Integer totalprice;
    private Boolean depositpaid;
    private BookingDates bookingdates;
    private String additionalneeds;

    public Booking() {
    }

    // --- Builder fluido para datos de prueba legibles -----------------------

    public static Booking unaReserva() {
        return new Booking();
    }

    public Booking conNombre(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
        return this;
    }

    public Booking conPrecio(int totalprice) {
        this.totalprice = totalprice;
        return this;
    }

    public Booking conDeposito(boolean depositpaid) {
        this.depositpaid = depositpaid;
        return this;
    }

    public Booking conFechas(String checkin, String checkout) {
        this.bookingdates = new BookingDates(checkin, checkout);
        return this;
    }

    public Booking conNecesidades(String additionalneeds) {
        this.additionalneeds = additionalneeds;
        return this;
    }

    // --- Getters / setters (Jackson los usa para serializar) ----------------

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Integer totalprice) {
        this.totalprice = totalprice;
    }

    public Boolean getDepositpaid() {
        return depositpaid;
    }

    public void setDepositpaid(Boolean depositpaid) {
        this.depositpaid = depositpaid;
    }

    public BookingDates getBookingdates() {
        return bookingdates;
    }

    public void setBookingdates(BookingDates bookingdates) {
        this.bookingdates = bookingdates;
    }

    public String getAdditionalneeds() {
        return additionalneeds;
    }

    public void setAdditionalneeds(String additionalneeds) {
        this.additionalneeds = additionalneeds;
    }
}
