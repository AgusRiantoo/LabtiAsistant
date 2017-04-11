package app.ar.labtiassistant.models;

/**
 * Created by ghost on 01/04/17.
 */

public class Jadwal {
    int _id;
    String _hari;
    String _ruang;
    String _job;
    String _kelas;
    String _shift;
    String _jam;
    String _mata_praktikum;

    public Jadwal() {

    }

    public Jadwal(int _id, String _hari, String _ruang, String _job, String _kelas, String _shift, String _jam, String _mata_praktikum) {
        this._id = _id;
        this._hari = _hari;
        this._ruang = _ruang;
        this._job = _job;
        this._kelas = _kelas;
        this._shift = _shift;
        this._jam = _jam;
        this._mata_praktikum = _mata_praktikum;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_hari() {
        return _hari;
    }

    public void set_hari(String _hari) {
        this._hari = _hari;
    }

    public String get_ruang() {
        return _ruang;
    }

    public void set_ruang(String _ruang) {
        this._ruang = _ruang;
    }

    public String get_job() {
        return _job;
    }

    public void set_job(String _job) {
        this._job = _job;
    }

    public String get_kelas() {
        return _kelas;
    }

    public void set_kelas(String _kelas) {
        this._kelas = _kelas;
    }

    public String get_shift() {
        return _shift;
    }

    public void set_shift(String _shift) {
        this._shift = _shift;
    }

    public String get_jam() {
        return _jam;
    }

    public void set_jam(String _jam) {
        this._jam = _jam;
    }

    public String get_mata_praktikum() {
        return _mata_praktikum;
    }

    public void set_mata_praktikum(String _mata_praktikum) {
        this._mata_praktikum = _mata_praktikum;
    }
}
