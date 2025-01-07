import { DataTypes } from 'sequelize';
import sequelize from '../config/database.js';

const Participante = sequelize.define('Participante', {
    id: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
    },
    id_intercambio: {
        type: DataTypes.INTEGER,
        allowNull: false,
        references: {
            model: 'Intercambio', // Nombre de la tabla `Intercambio`
            key: 'id',
        },
    },
    nombre: {
        type: DataTypes.STRING,
        allowNull: false,
    },
    correo: {
        type: DataTypes.STRING,
        allowNull: false,
    },
    telefono: {
        type: DataTypes.STRING,
        allowNull: true,
    },
    temaPreferido: {
        type: DataTypes.STRING,
        allowNull: true,
    },
}, {
    tableName: 'participantes',
    timestamps: false,
});

export default Participante;
