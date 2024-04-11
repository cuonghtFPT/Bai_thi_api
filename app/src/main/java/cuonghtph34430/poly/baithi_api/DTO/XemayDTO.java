package cuonghtph34430.poly.baithi_api.DTO;

public class XemayDTO {
    private String anhPh34430;
    private long  giaBanPh34430;
    private String mauSacPh34430;
    private String tenXePh34430;
    private String _id;

    public XemayDTO(String tenXePh34430, String mauSacPh34430, long giaBanPh34430, String anhPh34430) {
        this.tenXePh34430 = tenXePh34430;
        this.mauSacPh34430 = mauSacPh34430;
        this.giaBanPh34430 = giaBanPh34430;
        this.anhPh34430 = anhPh34430;
    }

    public String getAnhPh34430() {
        return anhPh34430;
    }

    public void setAnhPh34430(String anhPh34430) {
        this.anhPh34430 = anhPh34430;
    }

    public long getGiaBanPh34430() {
        return giaBanPh34430;
    }

    public void setGiaBanPh34430(long giaBanPh34430) {
        this.giaBanPh34430 = giaBanPh34430;
    }

    public String getMauSacPh34430() {
        return mauSacPh34430;
    }

    public void setMauSacPh34430(String mauSacPh34430) {
        this.mauSacPh34430 = mauSacPh34430;
    }

    public String getTenXePh34430() {
        return tenXePh34430;
    }

    public void setTenXePh34430(String tenXePh34430) {
        this.tenXePh34430 = tenXePh34430;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "XemayDTO{" +
                "ten='" + tenXePh34430 + '\'' +
                ", gia='" + giaBanPh34430 + '\'' + // Thay đổi từ brand thành description
                ", mau=" + mauSacPh34430 +
                ", anh='" + anhPh34430 + '\'' +
                ", id='" + _id + '\'' +
                '}';
    }
}
