# SPIN Transmission Demo

This repo contains a server that
* serves an RSA public key as JWK at `GET /jwk`
* offers an endpoint at `POST /decrypt` which decrypts incoming cipher text and returns the plain text

The repo also contains two client implementations that ought to demonstrate the integration:
* a browser/JS implementation that you can use by navigating to [http://localhost:8080/index.html](http://localhost:8080/index.html)
* a python client in `client.py` (install requirements.txt in a venv)


Start the containerized server by running
```bash
docker run -p 8080:8080 jjasper/spin-transport
```

Or start it locally by running `./gradlew bootRun` (requires Java17).

