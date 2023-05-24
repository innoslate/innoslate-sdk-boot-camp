package boot.innoslate.test;

import boot.innoslate.core.InnoslateRemote;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
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
    final String projectName = "Create me so I can Die";
    final String projectDescription = "Testing project creation";

    InnoslateRemote.createNewProject(projectName, projectDescription);

    //Create some entities in your project
  }

  /**
   * Shows how to create an Excel document for reports
   */
  @Test
  public void writeToExcel() throws IOException {
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet("Innoslate Excel's Sheet");

    //Adding values to the sheet
    Row row = sheet.createRow(0);
    //Cell row 0, column 0
    Cell cell = row.createCell(0);
    cell.setCellValue("Testing");
    //Cell row 0, column 1
    cell = row.createCell(1);
    cell.setCellValue("Cell Creation");
    //Cell row 0, column 2
    cell = row.createCell(2);
    cell.setCellValue("in a excel document");
    //Setting up cell style's
    CellStyle style = workbook.createCellStyle();
    XSSFFont font = workbook.createFont();
    font.setFontName(XSSFFont.DEFAULT_FONT_NAME);
//    font.setItalic(true);
//    font.setBold(true);
//    font.setUnderline(FontUnderline.SINGLE);
    font.setFontHeightInPoints((short) 22);
    style.setFont(font);
    cell.setCellStyle(style);

    //Output the workbook to a file saved in the Output folder
    File file = new File("Output/excel.xlsx");
    FileOutputStream fileOutputStream = new FileOutputStream(file);
    workbook.write(fileOutputStream);
    workbook.close();
  }

  /**
   * Shows how to create a Word document for reports
   * More Information can be found here <a href="https://www.tutorialspoint.com/apache_poi_word/apache_poi_word_quick_guide.htm">...</a>
   */
  @Test
  public void writeToWordDoc() throws IOException {
    //Create the Word docx
    XWPFDocument document = new XWPFDocument();

    //Create a paragraph
    XWPFParagraph paragraph = document.createParagraph();
    //Create a run to enter text
    XWPFRun run = paragraph.createRun();
    run.setText("This is the first line!\r");
    run.addBreak();
    run.setFontSize(18);

    //Create a run to enter text
    XWPFRun run2 = paragraph.createRun();
    run2.setText("This is second line.");
    run2.setBold(true);

    //Create a paragraph
    XWPFParagraph paragraph2 = document.createParagraph();
    //Create a run to enter text
    XWPFRun run3 = paragraph2.createRun();
    run3.setText("This is third line. In a new paragraph");

    //Output the Word docx to a file saved in the Output folder
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
    final int projectId = 0; //Project ID of the project that will be deleted

    InnoslateRemote.deleteProject(projectId);
  }
}
