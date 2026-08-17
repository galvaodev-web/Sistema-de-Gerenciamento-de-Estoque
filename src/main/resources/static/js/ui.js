export const moeda=v=>Number(v||0).toLocaleString('pt-BR',{style:'currency',currency:'BRL'});
export const dataHora=v=>v?new Date(v).toLocaleString('pt-BR',{dateStyle:'short',timeStyle:'short'}):'—';
export const vazio=(colunas,texto='Nenhum registro encontrado')=>`<tr><td colspan="${colunas}" class="empty">${texto}</td></tr>`;
export function toast(msg,erro=false){const el=document.querySelector('#toast');el.textContent=msg;el.className=erro?'show error':'show';setTimeout(()=>el.className='',3000)}
export function movimentoRow(m,comObs=false){return `<tr><td><strong>${m.produtoNome}</strong><br><small>${m.produtoSku}</small></td><td><span class="badge ${m.tipo.toLowerCase()}">${m.tipo}</span></td><td>${m.tipo==='SAIDA'?'-':'+'}${m.quantidade}</td><td>${dataHora(m.dataMovimentacao)}</td>${comObs?`<td>${m.observacao||'—'}</td>`:''}</tr>`}
