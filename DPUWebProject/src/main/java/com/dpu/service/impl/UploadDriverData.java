package com.dpu.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.dpu.dao.DivisionDao;
import com.dpu.entity.Driver;
import com.dpu.util.FileReaderUtility;

@Component
public class UploadDriverData {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	DivisionDao divisionDao;

	/*
	 * @Autowired private PaymentDao paymentDao;
	 */

	@Autowired
	private FileReaderUtility fileReaderUtility;

	public static void main(String[] args) throws IOException {

		ApplicationContext context = new ClassPathXmlApplicationContext("dpu-servlet.xml");
		UploadDriverData uploadDriverData = context.getBean(UploadDriverData.class);
		uploadDriverData.readExcelData();
		
	}

	private void readExcelData() throws IOException {
		String excelFilePath = "src/main/resources/Driver_List.xls";
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

		Workbook workbook = new HSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();

		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();

			Driver driver = new Driver();
			int ColoumnCount = 0;
			while (cellIterator.hasNext()) {
				
				Cell cell = cellIterator.next();
				if(ColoumnCount == 0){
					driver.setFirstName(cell.getStringCellValue());
				}
				if(ColoumnCount == 1){
					driver.setLastName(cell.getStringCellValue());
				}
				if(ColoumnCount == 2){
					
				}
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					System.out.print(cell.getStringCellValue());
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					System.out.print(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_NUMERIC:
					System.out.print(cell.getNumericCellValue());
					break;
				}
				System.out.print(" - ");
			}
			System.out.println();
		}

		workbook.close();
		inputStream.close();
		
	}

	public void insertDriverData() throws IOException {

		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			insertDriverData(session, tx);
			/*
			 * List<Object[]> processingData = paymentDao.getProcessingFee(externalResourceEntity, session);
			 * generateFile(processingData);
			 */
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	private void insertDriverData(Session session, Transaction tx) {
		Set<String> recordSet = getFileRecords();
		/*ExternalResourceEntity externalResourceEntity = new ExternalResourceEntity();
		if (recordSet != null && !recordSet.isEmpty()) {
			Transaction tx = session.beginTransaction();
			try {
				externalResourceEntity.setUploadDate(new Date());
				paymentDao.saveExternalResource(externalResourceEntity, session);
				for (String row : recordSet) {
					TransactionEntity transactionObj = createObject(row);
					transactionObj.setExternalResource(externalResourceEntity);
					paymentDao.saveTransaction(transactionObj, session);
				}
			} catch (Exception e) {
				if (tx != null) {
					tx.rollback();
				}
			} finally {
				if (tx != null) {
					tx.commit();
				}
			}
		}

		return externalResourceEntity;*/
	}

/*	private Driver createObject(String row) throws ParseException {
		Driver transaction = new Driver();
		String[] column = row.split(",");
		transaction.setExternalTransactionId(column[0]);
		transaction.setClientId(column[1]);
		transaction.setSecretId(column[2]);
		transaction.setTransactionType(column[3]);
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		Date date = formatter.parse(column[4]);
		transaction.setTransactionDate(date);
		if ("Y".equalsIgnoreCase(column[6])) {
			transaction.setPriorty(true);
		} else {
			transaction.setPriorty(false);
		}
		transaction.setMarketValue(Double.parseDouble(column[5]));
		return transaction;
	}*/

	private Set<String> getFileRecords() {
		File file = fileReaderUtility.readFile();
		Set<String> recordSet = new LinkedHashSet<String>();
		try {
			Scanner scanner = new Scanner(file);
			int count = 0;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (count != 0) {
					recordSet.add(line);
				}
				count++;
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return recordSet;
	}
}
