const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
const dotenv = require('dotenv');
const User = require('../models/user');

dotenv.config();

const generateToken = (userId) => {
    return jwt.sign({ id: userId }, process.env.JWT_SECRET, {
        expiresIn: process.env.JWT_EXPIRES_IN,
    });
};

const registerUser = async (req, res) => {
    try {
        const { username, email, password } = req.body;

        const existingUser = await User.findByUsernameOrEmail(username, email);
        if (existingUser) {
            return res.status(400).json({ error: 'Akun sudah terdaftar' });
        }
        
        const hashedPassword = await bcrypt.hash(password, 8);

        const userId = await User.create(username, email, hashedPassword);

        const token = generateToken(userId);

        res.status(201).json({ message: 'Pengguna berhasil didaftarkan', token });
    } catch (error) {
        console.error('Error saat mendaftarkan pengguna', error);
        res.status(500).json({ error: 'Proses registrasi gagal', details: error.message });
    }
};

const loginUser = async (req, res) => {
    try {
        const { email, password } = req.body;

        // Cari pengguna di database
        const user = await User.findByEmail(email);
        if (!user) {
            return res.status(404).json({ error: 'Pengguna tidak ditemukan' });
        }

        // Validasi password
        const isPasswordValid = await bcrypt.compare(password, user.password);
        if (!isPasswordValid) {
            return res.status(401).json({ error: 'Password tidak valid' });
        }

        // Generate token
        const token = generateToken(user.id);

        res.status(200).json({ 
            message: 'Login berhasil', 
            token 
        });
    } catch (error) {
        console.error('Error saat login pengguna:', error)
        res.status(500).json({ error: 'Login gagal', details: error.message });
    }
};

module.exports = { registerUser, loginUser };