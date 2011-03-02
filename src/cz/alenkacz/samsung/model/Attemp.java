package cz.alenkacz.samsung.model;

public class Attemp {
	
	private String _name;
	private String _email;
	private String _tel;
	private String _datetime;
	private String _text;
	
	public Attemp(String _name, String _email, String _tel, String _datetime,
			String _text) {
		super();
		this._name = _name;
		this._email = _email;
		this._tel = _tel;
		this._datetime = _datetime;
		this._text = _text;
	}
	public String get_name() {
		return _name;
	}
	public String get_email() {
		return _email;
	}
	public String get_tel() {
		return _tel;
	}
	public String get_datetime() {
		return _datetime;
	}
	public String get_text() {
		return _text;
	}
	public void set_name(String _name) {
		this._name = _name;
	}
	public void set_email(String _email) {
		this._email = _email;
	}
	public void set_tel(String _tel) {
		this._tel = _tel;
	}
	public void set_datetime(String _datetime) {
		this._datetime = _datetime;
	}
	public void set_text(String _text) {
		this._text = _text;
	}
}
