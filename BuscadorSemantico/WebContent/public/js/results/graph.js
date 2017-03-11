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
					color: '#57169a'
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
			Organizacao: {
				shape: 'icon',
				icon: {
					face: 'Ionicons',
					code: '\uf276',
					size: 50,
					color: '#57169a'
				}
			},// ----------
			Data: {
				shape: 'icon',
				icon: {
					face: 'Ionicons',
					code: '\uf2d1',
					size: 50,
					color: '#57169a'
				}
			},
			Duvida: {
				shape: 'icon',
				icon: {
					face: 'Ionicons',
					code: '\uf445',
					size: 50,
					color: '#57169a'
				}
			},
			Local: {
				shape: 'icon',
				icon: {
					face: 'Ionicons',
					code: '\uf455',
					size: 50,
					color: '#57169a'
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
			URLFonte: {
				shape: 'icon',
				icon: {
					face: 'Ionicons',
					code: '\uf347',
					size: 50,
					color: '#57169a'
				}
			},
			TempoFonte: {
				shape: 'icon',
				icon: {
					face: 'Ionicons',
					code: '\uf220',
					size: 50,
					color: '#57169a'
				}
			},
			Fonte: {
				shape: 'icon',
				icon: {
					face: 'Ionicons',
					code: '\uf12e',
					size: 50,
					color: '#57169a'
				}
			},
			Evento: {
				shape: 'icon',
				icon: {
					face: 'Ionicons',
					code: '\uf11a',
					size: 50,
					color: '#57169a'
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
			},
			Artefato: {
				shape: 'icon',
				icon: {
					face: 'Ionicons',
					code: '\uf2ad',
					size: 50,
					color: '#57169a'
				}
			},
			AutorReporter: {
				shape: 'icon',
				icon: {
					face: 'Ionicons',
					code: '\uf417',
					size: 50,
					color: '#57169a'
				}
			},
			Pesquisador: {
				shape: 'icon',
				icon: {
					face: 'Ionicons',
					code: '\uf21f',
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