import { DataTypes } from 'sequelize';
import sequelize from '../config/database.js';

const Tema = sequelize.define('Tema', {
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
    tema: {
        type: DataTypes.STRING,
        allowNull: false,
    },
}, {
    tableName: 'temas',
    timestamps: false,
});

export default Tema;
