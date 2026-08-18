const CHAVE = 'stockflow-demo-v1';

const agora = () => new Date().toISOString();
const inicial = () => ({
  categorias: [
    { id: 1, nome: 'Informática', descricao: 'Equipamentos e periféricos' },
    { id: 2, nome: 'Escritório', descricao: 'Materiais para o dia a dia' },
    { id: 3, nome: 'Acessórios', descricao: 'Acessórios diversos' }
  ],
  produtos: [
    { id: 1, nome: 'Notebook Pro 14', descricao: 'Notebook corporativo', codigoSku: 'NOT-001', categoriaId: 1, quantidade: 12, estoqueMinimo: 5, precoCompra: 3200, precoVenda: 4499.90, ativo: true, dataCadastro: agora() },
    { id: 2, nome: 'Teclado Mecânico', descricao: 'Teclado ABNT2', codigoSku: 'TEC-002', categoriaId: 1, quantidade: 4, estoqueMinimo: 5, precoCompra: 180, precoVenda: 299.90, ativo: true, dataCadastro: agora() },
    { id: 3, nome: 'Cadeira Ergonômica', descricao: 'Cadeira com apoio lombar', codigoSku: 'CAD-003', categoriaId: 2, quantidade: 8, estoqueMinimo: 2, precoCompra: 620, precoVenda: 949.90, ativo: true, dataCadastro: agora() },
    { id: 4, nome: 'Mouse sem fio', descricao: 'Mouse 2.4 GHz', codigoSku: 'MOU-004', categoriaId: 3, quantidade: 3, estoqueMinimo: 4, precoCompra: 65, precoVenda: 119.90, ativo: true, dataCadastro: agora() }
  ],
  movimentos: [
    { id: 1, produtoId: 1, tipo: 'ENTRADA', quantidade: 12, dataMovimentacao: agora(), observacao: 'Estoque inicial' },
    { id: 2, produtoId: 2, tipo: 'SAIDA', quantidade: 2, dataMovimentacao: agora(), observacao: 'Pedido interno' }
  ]
});

const ler = () => {
  const salvo = localStorage.getItem(CHAVE);
  if (salvo) return JSON.parse(salvo);
  const dados = inicial(); localStorage.setItem(CHAVE, JSON.stringify(dados)); return dados;
};
const salvar = dados => localStorage.setItem(CHAVE, JSON.stringify(dados));
const proximoId = itens => Math.max(0, ...itens.map(i => i.id)) + 1;
const respostaProduto = (p, dados) => ({ ...p, categoriaNome: dados.categorias.find(c => c.id === p.categoriaId)?.nome || 'Sem categoria', estoqueBaixo: p.quantidade <= p.estoqueMinimo });
const respostaMovimento = (m, dados) => { const p = dados.produtos.find(item => item.id === m.produtoId); return { ...m, produtoNome: p?.nome || 'Produto', produtoSku: p?.codigoSku || '—' }; };

export const demoApi = {
  async dashboard() { const d=ler(), ativos=d.produtos.filter(p=>p.ativo); return { produtosCadastrados:ativos.length, unidadesEmEstoque:ativos.reduce((s,p)=>s+p.quantidade,0), valorEstoque:ativos.reduce((s,p)=>s+p.quantidade*p.precoCompra,0), produtosEstoqueBaixo:ativos.filter(p=>p.quantidade<=p.estoqueMinimo).map(p=>respostaProduto(p,d)), ultimasMovimentacoes:d.movimentos.slice().reverse().slice(0,10).map(m=>respostaMovimento(m,d)), quantidadeEntradas:d.movimentos.filter(m=>m.tipo==='ENTRADA').length, quantidadeSaidas:d.movimentos.filter(m=>m.tipo==='SAIDA').length }; },
  async categorias() { return ler().categorias; },
  async salvarCategoria(item,id) { const d=ler(); if(id){const i=d.categorias.findIndex(c=>c.id===Number(id));d.categorias[i]={...d.categorias[i],...item};}else d.categorias.push({...item,id:proximoId(d.categorias)});salvar(d);return item; },
  async excluirCategoria(id) { const d=ler();if(d.produtos.some(p=>p.categoriaId===Number(id)))throw new Error('A categoria possui produtos vinculados');d.categorias=d.categorias.filter(c=>c.id!==Number(id));salvar(d); },
  async produtos(nome='') { const d=ler();return d.produtos.filter(p=>p.ativo&&p.nome.toLowerCase().includes(nome.toLowerCase())).map(p=>respostaProduto(p,d)); },
  async produto(id) { const d=ler(),p=d.produtos.find(p=>p.id===Number(id));if(!p)throw new Error('Produto não encontrado');return respostaProduto(p,d); },
  async salvarProduto(item,id) { const d=ler();if(d.produtos.some(p=>p.codigoSku.toLowerCase()===item.codigoSku.toLowerCase()&&p.id!==Number(id)))throw new Error('Já existe um produto com este SKU');if(id){const i=d.produtos.findIndex(p=>p.id===Number(id));d.produtos[i]={...d.produtos[i],...item};}else d.produtos.push({...item,id:proximoId(d.produtos),dataCadastro:agora()});salvar(d);return item; },
  async excluirProduto(id) { const d=ler(),p=d.produtos.find(p=>p.id===Number(id));if(p)p.ativo=false;salvar(d); },
  async movimentos(tipo='') { const d=ler();return d.movimentos.filter(m=>!tipo||m.tipo===tipo).slice().reverse().map(m=>respostaMovimento(m,d)); },
  async movimentar(tipo,item) { const d=ler(),p=d.produtos.find(p=>p.id===item.produtoId);if(!p)throw new Error('Produto não encontrado');const t=tipo.toUpperCase();if(t==='SAIDA'&&item.quantidade>p.quantidade)throw new Error('Estoque insuficiente para esta saída');p.quantidade+=t==='ENTRADA'?item.quantidade:-item.quantidade;const m={...item,id:proximoId(d.movimentos),tipo:t,dataMovimentacao:agora()};d.movimentos.push(m);salvar(d);return respostaMovimento(m,d); }
};
