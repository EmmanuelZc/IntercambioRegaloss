import { DataTypes } from 'sequelize';
import db from '../config/database.js';
import Temas from './tema.js';
const Intercambio = db.define('intercambio', {
    id: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true
    },
    nombre_intercambio: {
        type: DataTypes.STRING,
        allowNull: false
    },
    clave_unica: {
        type: DataTypes.STRING(8),
        allowNull: false,
        unique: true
    },
    id_user: {
        type: DataTypes.INTEGER,
        allowNull: false
    },
    fecha_limite_registro: {
        type: DataTypes.DATE,
        allowNull: false
    },
    fecha_intercambio: {
        type: DataTypes.DATE,
        allowNull: false
    },
    hora_intercambio: {
        type: DataTypes.TIME,
        allowNull: false
    },
    lugar_intercambio: {
        type: DataTypes.STRING,
        allowNull: false
    },
    monto: {
        type: DataTypes.FLOAT,
        allowNull: false
    },
    comentarios: {
        type: DataTypes.TEXT,
        allowNull: true
    }
}, {
    timestamps: false,
    tableName: 'intercambio'
});


export default Intercambio;
