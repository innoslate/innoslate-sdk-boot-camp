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

public class AdvancedTest {
  @Test
  public void createNewProject() {
    final String projectName = ""; // Pick a name for the new project (i.e. "Create Me to Delete Me")
    final String projectDescription = ""; // Pick a description for the new project (i.e. "Testing project creation")
    final String projectFolder = ""; // Pick a folder name for the new project (i.e. "Default")

    InnoslateRemote.createNewProject(projectName, projectDescription, projectFolder);

    // Create entities in your project
  }

  /**
   * Shows how to create an Excel document for reports
   */
  @Test
  public void writeToExcel() throws IOException {
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet("Innoslate Excel Sheet");

    // Adding values to the sheet
    Row row = sheet.createRow(0);
    // Cell row 0, column 0
    Cell cell = row.createCell(0);
    cell.setCellValue("Testing");
    // Cell row 0, column 1
    cell = row.createCell(1);
    cell.setCellValue("Cell Creation");
    // Cell row 0, column 2
    cell = row.createCell(2);
    cell.setCellValue("in an excel document");
    // Setting up cell styles
    CellStyle style = workbook.createCellStyle();
    XSSFFont font = workbook.createFont();
    font.setFontName(XSSFFont.DEFAULT_FONT_NAME);
    // Comment in the lines below to apply bold/italics/underline style to the text
    // font.setBold(true);
    // font.setItalic(true);
    // font.setUnderline(FontUnderline.SINGLE);
    font.setFontHeightInPoints((short) 22);
    style.setFont(font);
    cell.setCellStyle(style);

    // Output the workbook to a file saved in the "output" folder
    File xlsxFile = new File("output/excel.xlsx");
    if (!xlsxFile.getParentFile().exists()) {
      xlsxFile.getParentFile().mkdirs();
    }
    FileOutputStream fileOutputStream = new FileOutputStream(xlsxFile);
    workbook.write(fileOutputStream);
    workbook.close();
  }

  /**
   * Shows how to create a Word document for reports
   * More Information can be found here <a href="https://www.tutorialspoint.com/apache_poi_word/apache_poi_word_quick_guide.htm">...</a>
   */
  @Test
  public void writeToWordDoc() throws IOException {
    // Create the Word docx
    XWPFDocument document = new XWPFDocument();

    // Create a paragraph
    XWPFParagraph paragraph = document.createParagraph();

    // Create a run to enter text
    XWPFRun run = paragraph.createRun();
    run.setText("This is the first line!\n");
    run.addBreak();
    run.setFontSize(18);

    // Create a run to enter text
    XWPFRun run2 = paragraph.createRun();
    run2.setText("This is the second line.");
    run2.setBold(true);

    // Create a paragraph
    XWPFParagraph paragraph2 = document.createParagraph();

    // Create a run to enter text
    XWPFRun run3 = paragraph2.createRun();
    run3.setText("This is the third line...in a new paragraph!");

    // Output the Word docx to a file saved in the "output" folder
    File docxFile = new File("output/word.docx");
    if (!docxFile.getParentFile().exists()) {
      docxFile.getParentFile().mkdirs();
    }
    FileOutputStream fos = new FileOutputStream(docxFile);
    document.write(fos);
    document.close();
    fos.close();
  }

  /**
   * Deletes an Innoslate project
   */
  @Test
  public void deleteProject() {
    final int projectId = 0; // Project ID of the project that will be deleted

    InnoslateRemote.deleteProject(projectId);
  }
}
