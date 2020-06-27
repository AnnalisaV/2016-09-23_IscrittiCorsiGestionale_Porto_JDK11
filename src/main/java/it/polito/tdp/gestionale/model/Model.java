package it.polito.tdp.gestionale.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.gestionale.db.DidatticaDAO;

public class Model {

	private Map<String, Corso> idMapCorsi; 
	private Map<Integer, Studente> idMapStudenti; 
	private DidatticaDAO dao; 
	private Graph<Nodo, DefaultEdge> graph; 
	
	public Model() {
		this.dao= new DidatticaDAO();
		this.idMapCorsi= new HashMap<>(); 
		this.idMapStudenti= new HashMap<>();
		
		dao.getAllCorsi(idMapCorsi);
		dao.getAllStudenti(idMapStudenti);
		
	}
	
	public void creaGrafo() {
		this.graph= new SimpleGraph<>(DefaultEdge.class); 
		
		//vertex
		Graphs.addAllVertices(graph,  idMapStudenti.values()); 
		Graphs.addAllVertices(graph, idMapCorsi.values()); 
		
		//edges
		for (CorsoStudente cs : dao.getAdiacenze(idMapStudenti, idMapCorsi)) {
			DefaultEdge edge= graph.getEdge(cs.getCorso(), cs.getStudente()); 
			if (edge==null) {
				graph.addEdge(cs.getCorso(), cs.getStudente()); 
			}
		}
	}
	
	public Map<Integer, Integer> getFrequenza(){
		Map<Integer, Integer> frequenze= new HashMap<>(); //  numero corsi frequentati, numero studenti 
		
		for (Nodo n : graph.vertexSet()) {
			if (n instanceof Studente) {
				int corsiDelloStudente= Graphs.neighborListOf(graph, n).size();
				if (!frequenze.containsKey(corsiDelloStudente)) {
					frequenze.put(corsiDelloStudente, 1); //allora e' l'unico studente epr ora ad avere quel numero di corsi frequentati (per ora) 
				}
				else {
					//ci sono gia' studenti con quel numero di corsi frequentati
					int quantiStudenti= frequenze.get(corsiDelloStudente); 
					frequenze.put(corsiDelloStudente,quantiStudenti+1 ); 
				}
			}
		}
		
		return frequenze; 
		
	}
	
}
