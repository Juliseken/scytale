# Crypto From Scratch – RSA & ECC in Java

This repository contains an **educational implementation of public-key cryptography**, built **from scratch in Java**.

The goal of this project is **deep understanding**, not performance or production readiness.
Core cryptographic concepts such as **RSA**, **Elliptic Curve Cryptography (ECC)** and **X.509 certificates** are implemented step by step, with a strong focus on the underlying **mathematics** and **theoretical foundations**.

## Motivation

In everyday software development, cryptography is often used through high-level libraries and tools (e.g. OpenSSL, Java Cryptography Architecture, TLS stacks).

This project aims to answer questions like:

- How is an RSA key pair actually generated?
- Why does RSA encryption work mathematically?
- How do elliptic curves enable public-key cryptography?
- What exactly happens when a browser verifies a certificate?
- What assumptions does cryptographic security rely on?

## Scope

Planned topics include:

- Number-theoretic foundations  
  - Modular arithmetic  
  - Extended Euclidean algorithm  
  - Modular inverses  
  - Probabilistic primality tests  

- RSA  
  - Key generation  
  - Encryption & decryption  
  - Digital signatures  

- Elliptic Curve Cryptography (ECC)  
  - Elliptic curves over finite fields  
  - Point addition and scalar multiplication  

- Public Key Infrastructure (PKI) *(optional / later phase)*  
  - Parsing PEM / DER encoded certificates  
  - ASN.1 structures  
  - X.509 certificate validation  

Where possible, implementations avoid using high-level cryptographic APIs and instead rely only on basic primitives (e.g. `BigInteger` for arithmetic).

## Disclaimer

**This project is for educational purposes only.**

- The code is **not** intended for production use.
- It has **not** been reviewed or audited for security.
- Do **not** use this code to protect real or sensitive data.

## License

This project is licensed under the **MIT License**.

## Status

🚧 **Work in progress**  
The project is currently in an early phase, starting with number-theoretic building blocks.

## Author

Created as a personal learning project to deepen understanding of cryptography, mathematics and computer science.
