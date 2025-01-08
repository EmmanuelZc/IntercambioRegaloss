import { DataTypes } from 'sequelize';
import db from '../config/database.js';

const Temas = db.define('temas', {
    id: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true
    },
    id_intercambio: {
        type: DataTypes.INTEGER,
        allowNull: false
    },
    tema: {
        type: DataTypes.STRING,
        allowNull: false
    }
}, {
    timestamps: false,
    tableName: 'temas'
});

export default Temas;
