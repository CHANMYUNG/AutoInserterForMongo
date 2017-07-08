# AutoInserterForMongo
MongoDB 사용자를 위한 Excel 파일 데이터자동 삽입 프로그램 / Excel file data auto insertion program for MongoDB users

## 버전정보
- DEMO : ```04/07/2017```

## 사용법
> 그냥 GUI따라 사용하시면 됩니다.  
  
> 선택한 컬렉션에 내용이 존재하는경우 모두 삭제합니다. ```10/07/2017```

## 변명
급하게 만드느라 구조가 진짜 더럽습니다. 만든 저도 못알아봐요. ```04/07/2017```  

## 수정해야할 것
- 구조 수정 ```진행 전```  
- 실수 타입 추가 ```진행 전```  

## 사용한 언어
Java  

## 사용한 라이브러리 / 프레임워크
- Java Swing  
- Apache poi-ooxml ```maven```  
- mongo-java-driver ```maven```  

## Maven Dependencis

```xml
<dependencies>

        <dependency>

            <groupId>org.mongodb</groupId>

            <artifactId>mongo-java-driver</artifactId>

            <version>2.10.1</version>

        </dependency>

        <!-- https://mvnrepository.com/artifact/org.apache.poi/poi-ooxml -->
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>3.9</version>
        </dependency>

    </dependencies>
```
