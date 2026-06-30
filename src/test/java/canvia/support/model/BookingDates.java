package canvia.support.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Fechas de una reserva (objeto anidado dentro de Booking).
 *
 * @JsonIgnoreProperties(ignoreUnknown = true): si la API agrega campos nuevos,
 * la deserialización no falla.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingDates {

    private String checkin;
    private String checkout;

    public BookingDates() {
    }

    public BookingDates(String checkin, String checkout) {
        this.checkin = checkin;
        this.checkout = checkout;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }
}
