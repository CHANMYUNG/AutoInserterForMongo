import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by NooHeat on 10/07/2017.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileSrc;
        String fileName;

        System.out.println("=======파일 경로를 입력하세요=======");
        fileSrc = scanner.nextLine();
        if (!fileSrc.endsWith("/")) fileSrc += "/";
        System.out.println("=======파일 이름을 입력하세요=======");
        fileName = scanner.nextLine();
        if (!fileName.endsWith(".xls")) fileName += ".xls";

        System.out.println("입력하신 파일 경로 : " + fileSrc + fileName);
        File file = new File(fileSrc + fileName);
        System.out.println(file.getName());
        HSSFWorkbook workbook;
        try {
            workbook = new HSSFWorkbook(new FileInputStream(file));

            Iterator<Row> rows = workbook.getSheetAt(0).rowIterator();

            rows.next();

            while (rows.hasNext()) {
                Row row = rows.next();
                row.createCell(3);
                Cell fullGovermentCell = row.getCell(1);
                setFullGovermentValue(fullGovermentCell);

                Cell govermentCell = row.getCell(3);
                setGovermentValue(govermentCell, fullGovermentCell);

            }

            workbook.write(new FileOutputStream(new File(fileSrc+"[OUTPUT]"+fileName)));
            System.out.println("완료");
        } catch (IOException e) {
            System.out.println("유효하지 않은 경로와 파일명입니다");
        }

    }

    public static void setFullGovermentValue(Cell cell) {
        String value = "";
        for (String s : cell.getStringCellValue().split(" ")) {
            if (!s.endsWith("학교")) value += s + " ";
        }
        cell.setCellValue(value);
    }

    public static void setGovermentValue(Cell cell, Cell fullGovermentCell) {
        try {
            System.out.println(fullGovermentCell.getStringCellValue());
            System.out.println(fullGovermentCell.getStringCellValue().split(" ")[0]);
            cell.setCellValue(fullGovermentCell.getStringCellValue().split(" ")[0]);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
