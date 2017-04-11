package app.ar.labtiassistant.models;

/**
 * Created by ghost on 03/04/17.
 */


public class Transaksi {
    int _id;
    String _job;
    String _tingkat;
    String _shift;
    String _upah;
    String _tanggal;
    String _date;

    public Transaksi(){

    }

    public Transaksi(int _id, String _job, String _tingkat, String _shift, String _upah, String _tanggal, String _date) {
        this._id = _id;
        this._job = _job;
        this._tingkat = _tingkat;
        this._shift = _shift;
        this._upah = _upah;
        this._tanggal = _tanggal;
        this._date = _date;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_job() {
        return _job;
    }

    public void set_job(String _job) {
        this._job = _job;
    }

    public String get_tingkat() {
        return _tingkat;
    }

    public void set_tingkat(String _tingkat) {
        this._tingkat = _tingkat;
    }

    public String get_shift() {
        return _shift;
    }

    public void set_shift(String _shift) {
        this._shift = _shift;
    }

    public String get_upah() {
        return _upah;
    }

    public void set_upah(String _upah) {
        this._upah = _upah;
    }

    public String get_tanggal() {
        return _tanggal;
    }

    public void set_tanggal(String _tanggal) {
        this._tanggal = _tanggal;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }
}
