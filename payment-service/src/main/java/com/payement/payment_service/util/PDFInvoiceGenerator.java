package com.payement.payment_service.util;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.payement.payment_service.dto.ReservationResponseDTO;
import com.payement.payment_service.entity.Payment;

import java.io.ByteArrayOutputStream;

public class PDFInvoiceGenerator {

    public static byte[] generateInvoice(Payment payment, ReservationResponseDTO reservation) throws Exception {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);

        document.open();

        // Title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Classy Stay Hotels - Invoice", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("\n"));

        // Invoice content
        Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        document.add(new Paragraph("Reservation Code: " + reservation.getReservationCode(), contentFont));
        document.add(new Paragraph("Guest Name: " + reservation.getGuestInfo().getName(), contentFont));
        document.add(new Paragraph("Email: " + reservation.getGuestInfo().getEmail(), contentFont));
        document.add(new Paragraph("Room Number: " + reservation.getRoomInfo().getRoomNumber(), contentFont));
        document.add(new Paragraph("Room Type: " + reservation.getRoomInfo().getRoomType(), contentFont));
        document.add(new Paragraph("Check-in: " + reservation.getCheckInDate(), contentFont));
        document.add(new Paragraph("Check-out: " + reservation.getCheckOutDate(), contentFont));
        document.add(new Paragraph("Number of Guests: " + reservation.getNumOfGuests(), contentFont));
        document.add(new Paragraph("\n"));

        document.add(new Paragraph("Total Amount Paid: ₹" + payment.getAmount(), contentFont));
        document.add(new Paragraph("Order ID: " + payment.getOrderId(), contentFont));
        document.add(new Paragraph("Receipt Number: " + payment.getReceipt(), contentFont));
        document.add(new Paragraph("Status: " + payment.getStatus(), contentFont));

        document.add(new Paragraph("\nThank you for choosing Classy Stay Hotels!", contentFont));

        document.close();
        return baos.toByteArray();
    }
}