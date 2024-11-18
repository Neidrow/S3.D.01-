/*
 * Filtre.java nov. 2024 IUT de Rodez Info2 TPD 2024-2025, pas de
 * copyright
 */
package museoflow.modele;

/**
 * Classe objet representant un filtre
 */
public class Filtre {

    private String periode;

    private Conferencier conferencier;

    private Exposition exposition;

    private String horaire;

    private boolean typeConferencier;

    private boolean typeExposition;

    /**
     * Constructeur créant un filtre
     * 
     * @param periode
     * @param conferencier
     * @param exposition
     * @param horaire
     * @param typeConferencier
     * @param typeExposition
     */
    public Filtre(String periode, Conferencier conferencier,
            Exposition exposition, String horaire, boolean typeConferencier,
            boolean typeExposition) {
        this.periode = periode;
        this.conferencier = conferencier;
        this.exposition = exposition;
        this.horaire = horaire;
        this.typeConferencier = typeConferencier;
        this.typeExposition = typeExposition;
    }

}
