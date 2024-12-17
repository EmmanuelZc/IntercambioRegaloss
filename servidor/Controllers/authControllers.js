import  User from '../models/users.js'; // El modelo de usuario
import  bcrypt from 'bcrypt';

const registerUser = async (req, res) => {
    const { nombre, alias, correo, contraseña } = req.body;

    // Validación simple
    if (!nombre || !alias || !correo || !contraseña) {
        return res.status(400).json({ message: 'Faltan datos' });
    }

    // Encriptar la contraseña antes de guardarla
    const hashedPassword = await bcrypt.hash(contraseña, 10);

    // Crear el nuevo usuario
    try {
        const user = new User({
            nombre,
            alias,
            correo,
            contraseña: hashedPassword
        });

        await user.save();
        res.json({ message: 'Usuario registrado con éxito' });
    } catch (err) {
        console.error(err);
        res.status(500).json({ message: 'Error al registrar el usuario' });
    }
};




export default registerUser ;
