# SPIN Transmission Demo

This repo contains a server that
* serves an RSA public key as JWK at `GET /jwk`
* offers an endpoint at `POST /decrypt` which will decrypt incoming ciphertext and return it

Start the server by running `./gradlew bootRun`.

This repo also contains a client written in python.
It will read a plain text, encrypt it, and send it to the server.
```
# activate venv
pip install -r requirements.txt
python client.py
```
