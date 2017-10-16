package customclasses;

/**
 * Created by shery on 12/16/2016.
 */

public class Document {
    public static final String DOCUMENT_TABLE = "Document";
    public static final String KEY_DOCUMENT_ID = "DocumentId";
    public static final String KEY_LOAN_ID = "Loan_Id";
    public static final String KEY_IMAGE_NAME = "ImageName";
    public static final String KEY_IMAGE = "Image";
    public static final String KEY_UNI_CODE = "Unique_Code";
    public static final String KEY_DOC_ADDED_DATETIME = "DocumentAddedDateTime";
    private int DocumentId, CustomerId, LoanId;
    private String NicNumber, ImageName, Image, DocumentAddedDataTime, UniqueCode;

    public int getLoanId() {
        return LoanId;
    }

    public String getNicNumber() {
        return NicNumber;
    }

    public void setNicNumber(String nicNumber) {
        this.NicNumber = nicNumber;
    }

    public void setLoanId(int loanId) {
        this.LoanId = loanId;
    }

    public int getDocumentId() {
        return DocumentId;
    }

    public void setDocumentId(int documentId) {
        this.DocumentId = documentId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        this.CustomerId = customerId;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        this.ImageName = imageName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public String getDocumentAddedDataTime() {
        return DocumentAddedDataTime;
    }

    public void setDocumentAddedDataTime(String documentAddedDataTime) {
        this.DocumentAddedDataTime = documentAddedDataTime;
    }

    public String getUniqueCode() {
        return UniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        UniqueCode = uniqueCode;
    }

    public static String createDocumentTable = "CREATE TABLE IF NOT EXISTS " + DOCUMENT_TABLE +
            " (" + KEY_DOCUMENT_ID + " integer primary key autoincrement, "
            + KEY_LOAN_ID + " int(11), "
            + MicrofinanceCustomers.KEY_CUSTOMER_ID + " int(11), "
            + KEY_IMAGE_NAME + " text, "
            + KEY_IMAGE + " text, "
            + KEY_UNI_CODE + " text, "
            + KEY_DOC_ADDED_DATETIME + " text)";
}
