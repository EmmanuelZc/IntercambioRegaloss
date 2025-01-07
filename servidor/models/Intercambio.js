import { DataTypes } from 'sequelize';
import sequelize from '../config/database.js';

const Intercambio = sequelize.define('Intercambio', {
    id: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
    },
    nombre_intercambio: {
        type: DataTypes.STRING,
        allowNull: false,
    },
    clave_unica: {
        type: DataTypes.STRING,
        allowNull: false,
    },
    id_user: {
        type: DataTypes.INTEGER,
        allowNull: false,
    },
    fecha_limite_registro: {
        type: DataTypes.STRING, // Si ahora es VARCHAR
        allowNull: false,
    },
    fecha_intercambio: {
        type: DataTypes.STRING, // Si ahora es VARCHAR
        allowNull: false,
    },
    hora_intercambio: {
        type: DataTypes.STRING, // Si ahora es VARCHAR
        allowNull: false,
    },
    lugar_intercambio: {
        type: DataTypes.STRING,
        allowNull: false,
    },
    monto: {
        type: DataTypes.FLOAT,
        allowNull: false,
    },
    comentarios: {
        type: DataTypes.TEXT,
        allowNull: true,
    },
}, {
    timestamps: false,
    tableName: 'intercambio',
});

export default Intercambio;
