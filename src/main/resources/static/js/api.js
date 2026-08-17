const json = async (url, options={}) => {
  const response = await fetch(url,{headers:{'Content-Type':'application/json',...(options.headers||{})},...options});
  if(response.status===204) return null;
  const data = await response.json().catch(()=>null);
  if(!response.ok) throw new Error(data?.mensagem || Object.values(data?.campos||{})[0] || 'Não foi possível concluir a operação');
  return data;
};
export const api={
 dashboard:()=>json('/api/dashboard'), categorias:()=>json('/api/categorias'),
 salvarCategoria:(data,id)=>json(`/api/categorias${id?'/'+id:''}`,{method:id?'PUT':'POST',body:JSON.stringify(data)}),
 excluirCategoria:id=>json(`/api/categorias/${id}`,{method:'DELETE'}),
 produtos:(nome='')=>json('/api/produtos'+(nome?`?nome=${encodeURIComponent(nome)}`:'')), produto:id=>json(`/api/produtos/${id}`),
 salvarProduto:(data,id)=>json(`/api/produtos${id?'/'+id:''}`,{method:id?'PUT':'POST',body:JSON.stringify(data)}),
 excluirProduto:id=>json(`/api/produtos/${id}`,{method:'DELETE'}),
 movimentos:(tipo='')=>json('/api/movimentacoes'+(tipo?`?tipo=${tipo}`:'')),
 movimentar:(tipo,data)=>json(`/api/movimentacoes/${tipo}`,{method:'POST',body:JSON.stringify(data)})
};
