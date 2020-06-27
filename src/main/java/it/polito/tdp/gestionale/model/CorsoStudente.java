package it.polito.tdp.gestionale.model;

public class CorsoStudente {

	private Corso corso; 
	private Studente studente;
	/**
	 * @param corso
	 * @param studente
	 */
	public CorsoStudente(Corso corso, Studente studente) {
		super();
		this.corso = corso;
		this.studente = studente;
	}
	public Corso getCorso() {
		return corso;
	}
	public void setCorso(Corso corso) {
		this.corso = corso;
	}
	public Studente getStudente() {
		return studente;
	}
	public void setStudente(Studente studente) {
		this.studente = studente;
	}
	@Override
	public String toString() {
		return this.studente +" "+ this.corso;
	} 
	
	
	
}
