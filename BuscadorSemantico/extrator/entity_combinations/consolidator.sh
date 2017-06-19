
for i in "Artefato" "Grupo" "Acontecimento" "Pesquisador" "Evento" "Papel" "Fonte" "Organizacao" "Local" "Documento" "Pessoa" "Data" "Quantidade"; do grep -rh $i ./ | sort -t: -n -k2 | tail -1; done