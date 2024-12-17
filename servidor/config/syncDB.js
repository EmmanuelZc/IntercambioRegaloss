import sequelize from '../config/database.js';

const syncDB = async () => {
    try {
        await sequelize.sync({ force: false }); // Crea las tablas si no existen
        console.log('Base de datos sincronizada');
    } catch (error) {
        console.error('Error al sincronizar la base de datos:', error);
    }
};

export default syncDB;
