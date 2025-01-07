import Intercambio from '../models/Intercambio.js';

export const createIntercambio = async (req, res) => {
    const { 
        nombre, 
        fechaLimiteRegistro, 
        fechaIntercambio, 
        horaIntercambio, 
        lugar, 
        montoMaximo, 
        comentarios, 
        temas, 
        participantes 
    } = req.body;

    // Validar que todos los datos requeridos estén presentes
    if (!nombre || !fechaLimiteRegistro || !fechaIntercambio || !horaIntercambio || !lugar || montoMaximo === undefined) {
        return res.status(400).json({ message: 'Faltan datos obligatorios' });
    }

    try {
        // Crear un nuevo registro en la tabla `intercambios`
        const intercambio = await Intercambio.create({
            nombre,
            fechaLimiteRegistro,
            fechaIntercambio,
            horaIntercambio,
            lugar,
            montoMaximo,
            comentarios,
            temas,
            participantes,
            userId: req.userId, // Asumiendo que el middleware de autenticación añade `userId` a `req`
        });

        res.status(201).json({ message: 'Intercambio creado con éxito', intercambio });
    } catch (error) {
        console.error('Error al crear el intercambio:', error);
        res.status(500).json({ message: 'Error interno del servidor' });
    }
};
