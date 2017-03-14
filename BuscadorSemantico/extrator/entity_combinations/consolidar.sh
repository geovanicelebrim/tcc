
entities=('Artefato' 'Grupo' 'Acontecimento' 'Pesquisador' 'Evento' 'Papel' 'Fonte' 'Organizacao' 'Local''Documento' 'Pessoa' 'Data' 'Quantidade')
for i in "${entities[@]}"; do grep -rh $i ./ | sort -t: -n -k2 | tail -1; done