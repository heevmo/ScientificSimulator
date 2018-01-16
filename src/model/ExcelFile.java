package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

public class ExcelFile {

	private FileOutputStream fos;

	private List<ExcelFileDataSheet> listExcelFileDataSheet;

	public ExcelFile() {

		listExcelFileDataSheet = new ArrayList<>();

	}

	public void add(ExcelFileDataSheet e) {
		listExcelFileDataSheet.add(e);
	}

	public void setFile(File file) throws IOException {
		fos = new FileOutputStream(file);
		Workbook workbook = new SXSSFWorkbook();

		//
		for (ExcelFileDataSheet data : listExcelFileDataSheet) {
			Sheet sheet = workbook.createSheet(data.getGroupName());

			int phaseNumber = 0;
			int sequenceId = 0;
			int randomId = 0;
			int csParameterId = 0;
			

			//
			Row row = null;
			Cell cell = null;

			int rowNumber = 0;
			int cellNumber = 0;

			row = sheet.createRow(rowNumber);
			cell = row.createCell(cellNumber);

			// sheet title
			sheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, cellNumber, (cellNumber + 5)));

			CellStyle cs = workbook.createCellStyle();
			Font f = workbook.createFont();
			f.setFontHeightInPoints((short) 20);
			f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			f.setFontName("Courier New");
			cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			cs.setFont(f);
			cell.setCellStyle(cs);

			cell.setCellValue(file.getName());

			// CS parameter
			rowNumber += 3;
			cellNumber = 0;

			row = sheet.createRow(rowNumber);
			cell = row.createCell(cellNumber);

			sheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, cellNumber, (cellNumber + 1)));

			cs = workbook.createCellStyle();
			f = workbook.createFont();
			f.setFontHeightInPoints((short) 14);
			f.setFontName("Courier New");
			cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			cs.setFont(f);
			cell.setCellStyle(cs);

			cell.setCellValue("CS alpha:");

			for (String s : data.getMapStimulusAlpha().keySet()) {

				rowNumber += 1;
				cellNumber = 0;

				row = sheet.createRow(rowNumber);
				cell = row.createCell(cellNumber);
				cs = workbook.createCellStyle();
				f = workbook.createFont();
				f.setFontHeightInPoints((short) 13);
				f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
				f.setFontName("Courier New");
				cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				cs.setFont(f);
				cell.setCellStyle(cs);
				
				cell.setCellValue(s);

				cellNumber++;
				cell = row.createCell(cellNumber);
				cs = workbook.createCellStyle();
				f = workbook.createFont();
				f.setFontHeightInPoints((short) 11);
				f.setFontName("Verdana");
				cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				cs.setFont(f);
				cell.setCellStyle(cs);
				cell.setCellValue(data.getMapStimulusAlpha().get(s));
			}
			
			//discriminability
			
			rowNumber++;
			rowNumber++;
			cellNumber = 0;
			
			row = sheet.createRow(rowNumber);
			cell = row.createCell(cellNumber);
			cs = workbook.createCellStyle();
			f = workbook.createFont();
			f.setFontHeightInPoints((short) 14);
			f.setFontName("Courier New");
			cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			cs.setFont(f);
			cell.setCellStyle(cs);
			
			cell.setCellValue("D:");
			
			cellNumber++;
			cell = row.createCell(cellNumber);
			cs = workbook.createCellStyle();
			f = workbook.createFont();
			f.setFontHeightInPoints((short) 11);
			f.setFontName("Verdana");
			cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			cs.setFont(f);
			cell.setCellStyle(cs);
			cell.setCellValue(data.getDiscriminability());

			// group name
			rowNumber += 2;
			cellNumber = 0;

			row = sheet.createRow(rowNumber);
			cell = row.createCell(cellNumber);

			sheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, cellNumber, (cellNumber + 5)));
			
			cs = workbook.createCellStyle();
			f = workbook.createFont();
			f.setFontHeightInPoints((short) 18);
			f.setFontName("Courier New");
			f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			cs.setFont(f);
			cell.setCellStyle(cs);

			cell.setCellValue(data.getGroupName());
			
			//rowNumber++;

			// phase
			for (Map<String, List<Double>> map : data.getListMapStimulusPrediction()) {
				rowNumber += 2;
				cellNumber = 0;

				row = sheet.createRow(rowNumber);
				cell = row.createCell(cellNumber);

				sheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, cellNumber, (cellNumber + 5)));
				
				cs = workbook.createCellStyle();
				f = workbook.createFont();
				f.setFontHeightInPoints((short) 16);
				f.setFontName("Courier New");
				f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
				cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				cs.setFont(f);
				cell.setCellStyle(cs);

				cell.setCellValue("Phase " + ++phaseNumber);
				
				// US parameters
				rowNumber += 2;
				cellNumber = 0;

				row = sheet.createRow(rowNumber);
				cell = row.createCell(cellNumber);
				
				sheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, cellNumber, (cellNumber + 2)));
				
				cs = workbook.createCellStyle();
				f = workbook.createFont();
				f.setFontHeightInPoints((short) 14);
				f.setFontName("Courier New");
				cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				cs.setFont(f);
				cell.setCellStyle(cs);
				
				cell.setCellValue("US parameteres:");
				
				// beta+
				rowNumber++;
				cellNumber = 0;

				row = sheet.createRow(rowNumber);
				cell = row.createCell(cellNumber);
				
				cs = workbook.createCellStyle();
				f = workbook.createFont();
				f.setFontHeightInPoints((short) 13);
				f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
				f.setFontName("Courier New");
				cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				cs.setFont(f);
				cell.setCellStyle(cs);
				
				cell.setCellValue("beta+");
				
				cellNumber++;
				cell = row.createCell(cellNumber);
				
				cs = workbook.createCellStyle();
				f = workbook.createFont();
				f.setFontHeightInPoints((short) 11);
				f.setFontName("Verdana");
				cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				cs.setFont(f);
				cell.setCellStyle(cs);
				
				cell.setCellValue(new Double (data.getListCsParameter().get(csParameterId).getBetaPlus()));
				
				// beta-
				rowNumber++;
				cellNumber = 0;

				row = sheet.createRow(rowNumber);
				cell = row.createCell(cellNumber);
				
				cs = workbook.createCellStyle();
				f = workbook.createFont();
				f.setFontHeightInPoints((short) 13);
				f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
				f.setFontName("Courier New");
				cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				cs.setFont(f);
				cell.setCellStyle(cs);
				
				cell.setCellValue("beta-");
				
				cellNumber++;
				cell = row.createCell(cellNumber);
				
				cs = workbook.createCellStyle();
				f = workbook.createFont();
				f.setFontHeightInPoints((short) 11);
				f.setFontName("Verdana");
				cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				cs.setFont(f);
				cell.setCellStyle(cs);
				
				cell.setCellValue(new Double (data.getListCsParameter().get(csParameterId).getBetaMinus()));
				
				// lambda+
				rowNumber++;
				cellNumber = 0;

				row = sheet.createRow(rowNumber);
				cell = row.createCell(cellNumber);
				
				cs = workbook.createCellStyle();
				f = workbook.createFont();
				f.setFontHeightInPoints((short) 13);
				f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
				f.setFontName("Courier New");
				cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				cs.setFont(f);
				cell.setCellStyle(cs);
				
				cell.setCellValue("lambda+");
				
				cellNumber++;
				cell = row.createCell(cellNumber);
				
				cs = workbook.createCellStyle();
				f = workbook.createFont();
				f.setFontHeightInPoints((short) 11);
				f.setFontName("Verdana");
				cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				cs.setFont(f);
				cell.setCellStyle(cs);
				
				cell.setCellValue(new Double(data.getListCsParameter().get(csParameterId).getLambdaPlus()));
				
				// lambda-
				rowNumber++;
				cellNumber = 0;

				row = sheet.createRow(rowNumber);
				cell = row.createCell(cellNumber);
				
				cs = workbook.createCellStyle();
				f = workbook.createFont();
				f.setFontHeightInPoints((short) 13);
				f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
				f.setFontName("Courier New");
				cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				cs.setFont(f);
				cell.setCellStyle(cs);
				
				cell.setCellValue("lambda-");
				
				cellNumber++;
				cell = row.createCell(cellNumber);
				
				cs = workbook.createCellStyle();
				f = workbook.createFont();
				f.setFontHeightInPoints((short) 11);
				f.setFontName("Verdana");
				cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				cs.setFont(f);
				cell.setCellStyle(cs);
				
				cell.setCellValue(new Double (data.getListCsParameter().get(csParameterId).getLambdaMinus()));
				
				csParameterId++;
				
				// random
				
				rowNumber += 2;
				cellNumber = 0;

				row = sheet.createRow(rowNumber);
				cell = row.createCell(cellNumber);
				
				cs = workbook.createCellStyle();
				f = workbook.createFont();
				f.setFontHeightInPoints((short) 14);
				f.setFontName("Courier New");
				cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				cs.setFont(f);
				cell.setCellStyle(cs);
				sheet.setColumnWidth(cellNumber, (short) 4500);
				
				cell.setCellValue("Random: ");
				
				cellNumber++;
				cell = row.createCell(cellNumber);
				
				cs = workbook.createCellStyle();
				f = workbook.createFont();
				f.setFontHeightInPoints((short) 13);
				f.setFontName("Courier New");
				f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
				cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				cs.setFont(f);
				cell.setCellStyle(cs);
				
				cell.setCellValue(data.getListRandom().get(randomId++));
				//randomId--;
				
				// sequence
				rowNumber += 2;
				cellNumber = 0;

				row = sheet.createRow(rowNumber);
				cell = row.createCell(cellNumber);
				
				cs = workbook.createCellStyle();
				f = workbook.createFont();
				f.setFontHeightInPoints((short) 14);
				f.setFontName("Courier New");
				cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				cs.setFont(f);
				cell.setCellStyle(cs);
				sheet.setColumnWidth(cellNumber, (short) 3800);
				
				cell.setCellValue("Sequence: ");
				
				cellNumber++;
				cell = row.createCell(cellNumber);
				
				cs = workbook.createCellStyle();
				f = workbook.createFont();
				f.setFontHeightInPoints((short) 13);
				f.setFontName("Courier New");
				f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
				cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				cs.setFont(f);
				cell.setCellStyle(cs);
				
				sheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, cellNumber, (cellNumber + 4)));
				
				cell.setCellValue(data.getListSequence().get(sequenceId++));
				
				cellNumber = 0;
				// stimulus / prediction
				for (String s : map.keySet()) {
					
					rowNumber += 2;
					cellNumber++;
					
					row = sheet.createRow(rowNumber);
					cell = row.createCell(cellNumber);
					
					int trialNumber = 0;
					for (Double d : map.get(s)) {
						cell.setCellValue("Trial " + ++trialNumber);
						
						cs = workbook.createCellStyle();
						f = workbook.createFont();
						f.setFontHeightInPoints((short) 13);
						f.setFontName("Courier New");
						f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
						cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						cs.setFont(f);
						cell.setCellStyle(cs);
						sheet.setColumnWidth(cellNumber, (short) 3800);
						
						cellNumber++;
						
						cell = row.createCell(cellNumber);
					}
					
					rowNumber++;
					cellNumber = 0;
					
					row = sheet.createRow(rowNumber);
					
					cell = row.createCell(cellNumber);
					
					cs = workbook.createCellStyle();
					f = workbook.createFont();
					f.setFontHeightInPoints((short) 13);
					f.setFontName("Courier New");
					f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
					cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
					cs.setFont(f);
					cell.setCellStyle(cs);
					
					
					
					cell.setCellValue(s);
					
					for (Double d : map.get(s)) {
						
						cellNumber++;
						
						cell = row.createCell(cellNumber);
						
						cs = workbook.createCellStyle();
						f = workbook.createFont();
						f.setFontHeightInPoints((short) 11);
						f.setFontName("Verdana");
						cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						cs.setFont(f);
						cell.setCellStyle(cs);
						
						double round = new Double(String.format("%.5f", d));
						
						cell.setCellValue(round);
					}
					
					cellNumber = 0;	
				}
				
			}
		}

		workbook.write(fos);
		fos.close();

	}

}
