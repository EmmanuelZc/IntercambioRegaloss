import User from '../models/users.js';  // El modelo de usuario
import bcrypt from 'bcrypt';
import jwt from 'jsonwebtoken';

const registerUser = async (req, res) => {
    const { nombre, alias, correo, contraseña } = req.body;

    // Validación simple
    if (!nombre || !alias || !correo || !contraseña) {
        return res.status(400).json({ message: 'Faltan datos' });
    }

    // Verificar si el correo ya está registrado
    const existingUser = await User.findOne({ correo });
    if (existingUser) {
        return res.status(400).json({ message: 'El correo ya está registrado' });
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

const loginUser = async (req, res) => {
    const { correo, contraseña } = req.body;
    if (!correo || !contraseña) {
        return res.status(400).json({ message: 'Faltan datos' });
    }
    
    try {
        // Correcta estructura del objeto where
        const user = await User.findOne({
            where: { correo } // Aquí estamos pasando la condición correctamente dentro del objeto `where`
        });

        if (!user) {
            return res.status(404).json({ message: 'Usuario no encontrado' });
        }

        // Comparar la contraseña proporcionada con la encriptada en la base de datos
        const isPasswordValid = await bcrypt.compare(contraseña, user.contraseña);
        if (!isPasswordValid) {
            return res.status(401).json({ message: 'Contraseña incorrecta' });
        }

        // Si la contraseña es válida, generamos un token JWT
        const token = jwt.sign(
            { userId: user.id },  // El payload con la información del usuario
            process.env.JWT_SECRET,  // La clave secreta para firmar el token
            { expiresIn: '1h' }  // Tiempo de expiración del token (1 hora en este caso)
        );

        // Retornar el token y los detalles del usuario
        res.json({
            message: 'Inicio de sesión exitoso',
            token,  // El token JWT generado
            user: {
                nombre: user.nombre,
                alias: user.alias,
                correo: user.correo
            }
        });
    } catch (err) {
        console.error(err);
        res.status(500).json({ message: 'Error al iniciar sesión' });
    }
};



    
export default { registerUser, loginUser };
