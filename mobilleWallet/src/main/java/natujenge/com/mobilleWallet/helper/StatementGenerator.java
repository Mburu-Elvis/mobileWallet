package natujenge.com.mobilleWallet.helper;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import natujenge.com.mobilleWallet.service.dto.TransactionResponseDTO;
import natujenge.com.mobilleWallet.service.dto.UserRequestDTO;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StatementGenerator {
    public void generateStatement(List<TransactionResponseDTO> statements) throws FileNotFoundException, DocumentException {
        Document statement = new Document(PageSize.A4, 1, 1, 10, 4);

        PdfWriter.getInstance(statement, new FileOutputStream("statement.pdf"));

        statement.open();
//        statement.setPageSize(PageSize.A4, 10, 10, 10, 10);
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 32, BaseColor.GREEN);
        Paragraph h1 = new Paragraph("Mobile Wallet Statement", titleFont);
        h1.setAlignment(Element.ALIGN_CENTER);
        statement.add(h1);
        statement.add(new Paragraph("\n"));

//        PdfPTable kycDetails = new PdfPTable(2);
//        kycDetails.addCell("Customer Name");
//        kycDetails.addCell(userRequestDTO.getUsername());
//        kycDetails.addCell("Mobile Number");
//        kycDetails.addCell(userRequestDTO.getPhoneNumber());
//        kycDetails.addCell("Request Date");
//        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE.ofPattern("MMMM dd, yyyy HH:mm:ss");
//        String formattedDate = LocalDateTime.now().format(formatter);
//        kycDetails.addCell(formattedDate);
//        statement.add(kycDetails);

        statement.add(new Paragraph("\n"));
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8);

        PdfPTable transactions = new PdfPTable(5);
        transactions.addCell(new Phrase("transaction date", font));
        transactions.addCell(new Phrase("from", font));
        transactions.addCell(new Phrase("amount", font));
        transactions.addCell(new Phrase("to", font));
        transactions.addCell(new Phrase("description", font));

        font = FontFactory.getFont(FontFactory.HELVETICA, 6);

        for (TransactionResponseDTO dto: statements) {
            transactions.addCell(new Phrase(dto.getTransaction_date().toString(), font));
            transactions.addCell(new Phrase(dto.getFrom(), font));
            transactions.addCell(new Phrase(dto.getAmount().toString(), font));
            transactions.addCell(new Phrase(dto.getUser_received(), font));
            transactions.addCell(new Phrase(dto.getDescription(), font));
        }
        statement.add(transactions);
        statement.close();

    }
}
