function drawGraph(edges, nodesIO) {
				
	var optionsIO = {
		interaction: {tooltipDelay: 400},
		physics: {
			maxVelocity: 16,
			solver: 'forceAtlas2Based',
			timestep: 0.1,
			stabilization: {
				enabled:false,
				iterations:350,
				updateInterval:25
			}
		},
		groups: {
			Grupo: {
				shape: 'icon',
				icon: {
					face: 'Ionicons',
					code: '\uf47c',
					size: 50,
					color: '#0000cc'
				}
			},
			Pessoa: {
				shape: 'icon',
				icon: {
					face: 'Ionicons',
					code: '\uf47e',
					size: 50,
					color: '#57169a'
				}
			},
			Papel: {
				shape: 'icon',
				icon: {
					face: 'Ionicons',
					code: '\uf11f',
					size: 50,
					color: '#00cc99'
				}
			},
			Organizacao: {
				shape: 'icon',
				icon: {
					face: 'Ionicons',
					code: '\uf276',
					size: 50,
					color: '#006600'
				}
			},// ----------
			Data: {
				shape: 'icon',
				icon: {
					face: 'Ionicons',
					code: '\uf2d1',
					size: 50,
					color: '#003366'
				}
			},
			Local: {
				shape: 'icon',
				icon: {
					face: 'Ionicons',
					code: '\uf3a3',
					size: 50,
					color: '#e60000'
				}
			},
			Documento: {
				shape: 'icon',
				icon: {
					face: 'Ionicons',
					code: '\uf471',
					size: 50,
					color: '#f0a30a'
				}
			},
			Arquivo: {
				shape: 'icon',
				icon: {
					face: 'Ionicons',
					code: '\uf102',
					size: 50,
					color: '#fffff'
				}
			},
			Evento: {
				shape: 'icon',
				icon: {
					face: 'Ionicons',
					code: '\uf11a',
					size: 50,
					color: '#ff6600'
				}
			},
			Quantidade: {
				shape: 'icon',
				icon: {
					face: 'Ionicons',
					code: '\uf262',
					size: 50,
					color: '#57169a'
				}
			}
		}
	};
	
	// create a network
	var containerIO = document.getElementById('mynetwork');
	var dataIO = {
		nodes: nodesIO,
		edges: edges
	};

	var networkIO = new vis.Network(containerIO, dataIO, optionsIO);

	           /*
				 * //Dispara uma ação quando clica em um nó
				 * networkIO.on("click", function (params) { params.event =
				 * "[original event]";
				 * //document.getElementById('eventSpan').innerHTML = '<h2>Click
				 * event:</h2>' + JSON.stringify(params, null, 4); var text =
				 * JSON.stringify(params, null, 4); obj = JSON.parse(text);
				 * document.getElementById('eventSpan').innerHTML = '<h2>Click
				 * event:</h2>' + nodesIO.obj.nodes[0];
				 * 
				 * });
				 */
}