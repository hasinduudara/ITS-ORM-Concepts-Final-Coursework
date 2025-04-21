package lk.ijse.gdse.mentalhealth.bo.custom.impl;

import lk.ijse.gdse.mentalhealth.bo.custom.TherapyProgramBO;
import lk.ijse.gdse.mentalhealth.dao.custom.TherapistDAO;
import lk.ijse.gdse.mentalhealth.dao.custom.TherapyProgramDAO;
import lk.ijse.gdse.mentalhealth.dao.custom.impl.TherapistDAOImpl;
import lk.ijse.gdse.mentalhealth.dao.custom.impl.TherapyProgramDAOImpl;
import lk.ijse.gdse.mentalhealth.dto.TherapyProgramDTO;
import lk.ijse.gdse.mentalhealth.entity.TherapyProgram;

import java.sql.SQLException;
import java.util.ArrayList;

public class TherapyProgramBOImpl implements TherapyProgramBO {
    TherapyProgramDAO therapyProgramDAO = new TherapyProgramDAOImpl();
    TherapistDAO therapistDAO = new TherapistDAOImpl();

    @Override
    public boolean saveTherapyProgram(TherapyProgramDTO therapyProgramDTO) {
        return therapyProgramDAO.save(
                new TherapyProgram(
                        therapyProgramDTO.getProgramId(),
                        therapyProgramDTO.getProgramName(),
                        therapyProgramDTO.getDuration(),
                        therapyProgramDTO.getFee(),
                        new ArrayList<>(),
                        new ArrayList<>()
                )
        );
    }

    @Override
    public boolean updateTherapyProgram(TherapyProgramDTO therapyProgramDTO) {
        return therapyProgramDAO.update(
                new TherapyProgram(
                        therapyProgramDTO.getProgramId(),
                        therapyProgramDTO.getProgramName(),
                        therapyProgramDTO.getDuration(),
                        therapyProgramDTO.getFee(),
                        new ArrayList<>(),
                        new ArrayList<>()
                )
        );
    }

    @Override
    public boolean deleteTherapyProgram(String id) throws Exception {
        return therapyProgramDAO.deleteByPK(id);
    }

    @Override
    public ArrayList<TherapyProgramDTO> loadAllTherapyPrograms() throws SQLException, ClassNotFoundException {
        ArrayList<TherapyProgramDTO> therapyProgramDTOs = new ArrayList<>();
        ArrayList<TherapyProgram> therapyPrograms = (ArrayList<TherapyProgram>) therapyProgramDAO.getAll();

        for (TherapyProgram therapyProgram : therapyPrograms) {
            therapyProgramDTOs.add(
                    new TherapyProgramDTO(
                            therapyProgram.getProgramId(),
                            therapyProgram.getProgramName(),
                            therapyProgram.getDuration(),
                            therapyProgram.getFee()
                    )
            );
        }
        return therapyProgramDTOs;
    }

    @Override
    public String getNextTherapyProgramId() {
        return therapyProgramDAO.getNextId();
    }
}
