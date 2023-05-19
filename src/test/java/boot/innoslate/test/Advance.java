package boot.innoslate.test;

import boot.innoslate.core.InnoslateRemote;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Advance {
  @Test
  public void createNewProject() {
    final String projectName = "";
    final String projectDescription = "";

    InnoslateRemote.createNewProject(projectName, projectDescription);

    //Create some entities in your project
  }

  /**
   * Shows how to create an Excel document for reports
   */
  @Test
  public void writeToExcel() throws IOException, WriteException {
    File file = new File("Output/excel.xls");
    WritableWorkbook workbook = Workbook.createWorkbook(file);
    WritableSheet sheet = workbook.createSheet("Innoslate Excel's Sheet", 0);

    WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 11, WritableFont.NO_BOLD);
    WritableCellFormat headerCellFormat = new WritableCellFormat(headerFont);
    headerCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
    headerCellFormat.setAlignment(Alignment.CENTRE);
    headerCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
    headerCellFormat.setWrap(true);


    Label label = new Label(0, 0, "TEST", headerCellFormat);
    sheet.addCell(label);

    workbook.write();
    workbook.close();
  }

  /**
   * Shows how to create a Word document for reports
   * More Information can be found here <a href="https://www.tutorialspoint.com/apache_poi_word/apache_poi_word_quick_guide.htm">...</a>
   */
  @Test
  public void writeToWordDoc() throws IOException {
    XWPFDocument document = new XWPFDocument();
    XWPFParagraph paragraph = document.createParagraph();
    XWPFRun run = paragraph.createRun();
    run.setText("This is the first line!\r");
    run.addBreak();
    run.setFontSize(18);

    XWPFRun run2 = paragraph.createRun();
    run2.setText("This is second line.");
    run2.setBold(true);

    XWPFParagraph paragraph2 = document.createParagraph();
    XWPFRun run3 = paragraph2.createRun();
    run3.setText("This is third line. In a new paragraph");

    FileOutputStream fos = new FileOutputStream(new File("Output/word.doc"));
    document.write(fos);
    document.close();
    fos.close();
  }

  /**
   * Deletes a innoslate project
   */
  @Test
  public void deleteProject() {
    final int projectId = 0;

    InnoslateRemote.deleteProject(projectId);
  }
}
