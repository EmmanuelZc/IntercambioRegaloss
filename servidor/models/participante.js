import { DataTypes } from 'sequelize';
import db from '../config/database.js';
const Participante = db.define('participantes', {
    id: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
    },
    id_intercambio: {
        type: DataTypes.INTEGER,
        allowNull: false,
    },
    nombre: {
        type: DataTypes.STRING,
        allowNull: false,
    },
    email: {
        type: DataTypes.STRING,
        allowNull: false,
    },
    telefono: {
        type: DataTypes.STRING,
        allowNull: true,
    },
    confirmado: {
        type: DataTypes.INTEGER,
        allowNull: false,
        defaultValue: 0,
    },
    asignado_a: {
        type: DataTypes.INTEGER,
        allowNull: true,
    },
}, {
    timestamps: false,
    tableName: 'participantes',
});

export default Participante;
