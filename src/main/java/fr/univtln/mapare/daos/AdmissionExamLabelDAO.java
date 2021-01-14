package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.AdmissionExamLabel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe DAO d'un Intitulé de concours
 * @author Equipe MAPARE
 * @version 1.0
 */
public class AdmissionExamLabelDAO extends AbstractDAO<AdmissionExamLabel> {

    /**
     * Constructeur d'une DAO d'intitulé de Concours
     * @throws SQLException Exception SQL
     */
    public AdmissionExamLabelDAO() throws SQLException {
        super("INSERT INTO ADMISSIONEXAM_LABEL(LABEL) VALUES(?)",
            "UPDATE ADMISSIONEXAM_LABEL SET LABEL=? WHERE ID=?");
    }

    @Override
    protected AdmissionExamLabel fromResultSet(ResultSet resultSet) throws SQLException {
        for (AdmissionExamLabel a: AdmissionExamLabel.getAdmissionExamLabelList()) {
            if (a.getId() == resultSet.getLong("ID"))
                return a;
        }
        return new AdmissionExamLabel(resultSet.getInt("ID"),
                resultSet.getString("LABEL"));
    }

    @Override
    public AdmissionExamLabel persist(AdmissionExamLabel admissionExamLabel) throws SQLException {
        populate(persistPS, admissionExamLabel);
        return super.persist();
    }

    @Override
    public void update(AdmissionExamLabel admissionExamLabel) throws SQLException {
        populate(updatePS, admissionExamLabel);
        updatePS.setLong(2, admissionExamLabel.getId());
        super.update();
    }

    /**
     * Permet de peupler les Prepared Statements pour AdmissionExamLabel
     * @param popPS La Prepared Statements à peupler
     * @param admissionExamLabel l'Intitulé du concours
     * @throws SQLException Exception SQL
     */
    private void populate(PreparedStatement popPS, AdmissionExamLabel admissionExamLabel) throws SQLException {
        popPS.setString(1, admissionExamLabel.getLabel());
    }

    @Override
    public String getTableName() {
        return "ADMISSIONEXAM_LABEL";
    }
}
