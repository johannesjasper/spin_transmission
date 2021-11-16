# RSA OAEP Demo

This repo contains a server that
* serves an RSA public key and x509 certificate as JWK at `GET /api/publickey/spin`
* offers an endpoint at `POST /decrypt` which decrypts incoming cipher text and returns the plain text

The repo also contains two client implementations that ought to demonstrate the integration:
* a browser/JS implementation that you can use by navigating to [http://localhost:8080/index.html](http://localhost:8080/index.html)
* a python client in `client.py` (install requirements.txt in a venv)

It uses :
* RSA_OAEP
* SHA512
* a (hardcoded) RSA key with a length of 4096 bit

Start the containerized server by running
```bash
docker run -p 8080:8080 jjasper/spin-transport
```

Or start it locally by running `./gradlew bootRun` (requires Java17).

### Configuration

The certificate chain and the keypair are loaded from a Keystore file in the resources folder. 
The name, password, and alias can be set in the `application.yml`.
Please refer to `generate_certs.sh`, which will create a CA, an intermediate, and a server certificate and bundle them in a `spin.pfx`.
