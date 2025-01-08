import Intercambio from './Intercambio.js';
import Temas from './tema.js';
import Participante from './participante.js';

const setupAssociations = () => {
    // Relación entre Intercambio y Temas
    Intercambio.hasMany(Temas, {
        foreignKey: 'id_intercambio',
        as: 'temasIntercambio',
    });

    Temas.belongsTo(Intercambio, {
        foreignKey: 'id_intercambio',
        as: 'intercambio',
    });

    // Relación entre Intercambio y Participantes
    Intercambio.hasMany(Participante, {
        foreignKey: 'id_intercambio',
        as: 'participantesIntercambio',
    });

    Participante.belongsTo(Intercambio, {
        foreignKey: 'id_intercambio',
        as: 'intercambio',
    });
};

export default setupAssociations;
