import Intercambio from './Intercambio.js';
import Temas from './tema.js';
import Participante from './participante.js';

const setupAssociations = () => {
    // Relación Intercambio -> Temas
    Intercambio.hasMany(Temas, {
        foreignKey: 'id_intercambio',
        as: 'temas',
    });
    Temas.belongsTo(Intercambio, {
        foreignKey: 'id_intercambio',
        as: 'intercambio',
    });

    // Relación Intercambio -> Participante
    Intercambio.hasMany(Participante, {
        foreignKey: 'id_intercambio',
        as: 'participantes',
    });
    Participante.belongsTo(Intercambio, {
        foreignKey: 'id_intercambio',
        as: 'intercambio',
    });
};

export default setupAssociations;
