
#cleanup
rm intermediate.csr root.key root.pem intermediate.pem intermediate.key root.srl server.csr server.key server.pem all.pem chain.pem spin.pfx

openssl genrsa -out root.key 4096
openssl req -new -x509 -days 1826 -key root.key -out root.pem -subj "/C=DE/ST=Hamburg/L=Hamburg/CN=SPIN Demo CA"

openssl genrsa -out intermediate.key 4096
openssl req -new -key intermediate.key -out intermediate.csr -subj "/C=DE/ST=Hamburg/L=Hamburg/CN=SPIN Demo Intermediate"
openssl x509 -req -days 1000 -in intermediate.csr -CA root.pem -CAkey root.key -CAcreateserial -out intermediate.pem


openssl genrsa -out server.key 4096
openssl req -new -key server.key -out server.csr  -subj "/C=DE/ST=Hamburg/L=Hamburg/CN=SPIN Demo server"
openssl x509 -req -days 1000 -in server.csr -CA intermediate.pem -CAkey intermediate.key -set_serial 0101 -out server.pem -sha1

# place all intermediates and root into chain.pem
cat intermediate.pem root.pem > chain.pem
# place the the client cert, all intermediates, and the root into all.pem
cat server.pem intermediate.pem root.pem > all.pem

openssl pkcs12 -export \
        -name "spin_encryption"  \
        -in all.pem \
        -inkey server.key \
        -CAfile chain.pem \
        -passout pass:Test123! \
        -out spin.pfx

#cleanup
rm intermediate.csr root.key root.pem intermediate.pem intermediate.key root.srl server.csr server.key server.pem all.pem chain.pem
mv spin.pfx src/main/resources
