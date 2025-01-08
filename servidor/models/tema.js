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
        allowNull: false,
        references: {
            model: 'intercambio', // Asegúrate de que 'intercambio' es el nombre correcto en tu base de datos
            key: 'id'
        }
    },
    tema: {
        type: DataTypes.STRING,
        allowNull: false
    }
}, {
    timestamps: false, // Puedes habilitar timestamps si necesitas createdAt/updatedAt
    tableName: 'temas' // Asegúrate de que el nombre coincide con tu tabla en la BD
});

export default Temas;
