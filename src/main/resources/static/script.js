// Funções de modal (consolidadas sem duplicações)
function abrirModalRefeicao() {
    document.getElementById('modalRefeicao').style.display = 'flex';
    document.getElementById('data-refeicao').value = new Date().toISOString().split('T')[0];
    document.body.style.overflow = 'hidden';
}

function fecharModalRefeicao() {
    document.getElementById('modalRefeicao').style.display = 'none';
    document.body.style.overflow = 'auto';
}

function abrirModalExercicio() {
    document.getElementById('modalExercicio').style.display = 'flex';
    document.getElementById('dia').value = new Date().toISOString().split('T')[0];
    document.body.style.overflow = 'hidden';
}

function fecharModalExercicio() {
    document.getElementById('modalExercicio').style.display = 'none';
    document.body.style.overflow = 'auto';
}

// Eventos de clique nos overlays
document.querySelectorAll('.modal-overlay').forEach(modal => {
    modal.addEventListener('click', function(e) {
        if (e.target === this) {
            if (this.id === 'modalRefeicao') fecharModalRefeicao();
            if (this.id === 'modalExercicio') fecharModalExercicio();
        }
    });
});

// Calcular total de calorias
function calcularTotalCalorias() {
    const hoje = new Date().toISOString().split('T')[0];
    let total = 0;
    
    document.querySelectorAll('.tabela-refeicoes tbody tr').forEach(linha => {
        const dataRefeicao = linha.cells[3].textContent;
        const partesData = dataRefeicao.split('/');
        const dataFormatada = `${partesData[2]}-${partesData[1]}-${partesData[0]}`;
        
        if (dataFormatada === hoje) {
            total += parseInt(linha.cells[2].textContent);
        }
    });
    
    document.getElementById('total-calorias').textContent = total;
}

// Tudo dentro de um único document.ready
$(document).ready(function() {
    // Formulário de refeição
    $('#formRefeicao').submit(function(e) {
        e.preventDefault();
        const formData = {
            alimento: $('#alimento').val(),
            quantidade: $('#quantidade').val(),
            kcal: $('#kcal').val(),
            data: $('#data-refeicao').val(),
            hora: $('#hora-refeicao').val()
        };
        
        $.ajax({
            url: '/refeicoes',
            type: 'POST',
            data: formData,
            success: function(response) {
                alert(response);
                fecharModalRefeicao();
                location.reload();
            },
            error: function(xhr) {
                alert('Erro ao cadastrar refeição: ' + xhr.responseText);
            }
        });
    });

    // Formulário de exercício
    $('#formExercicio').submit(function(e) {
        e.preventDefault();
        const formData = {
            tipo: $('#tipo-exercicio').val(),
            duracao: parseInt($('#duracao').val()),
            intensidade: $('input[name="intensidade"]:checked').val(),
            dia: $('#dia').val(),
            hora: $('#hora').val()
        };
        
        $.ajax({
            url: '/atividades',
            type: 'POST',
            data: formData,
            success: function(response) {
                alert(response);
                fecharModalExercicio();
                location.reload();
            },
            error: function(xhr) {
                alert('Erro ao cadastrar atividade: ' + xhr.responseText);
            }
        });
    });

    // Botões de exclusão
    $('.btn-excluir').click(function() {
        const id = $(this).data('id');
        const tipo = $(this).closest('table').hasClass('tabela-refeicoes') ? 'refeicoes' : 'atividades';
        
        if (confirm('Tem certeza que deseja excluir este item?')) {
            $.ajax({
                url: `/${tipo}/${id}`,
                type: 'DELETE',
                success: function(response) {
                    alert(response);
                    location.reload();
                },
                error: function(xhr) {
                    alert('Erro ao excluir: ' + xhr.responseText);
                }
            });
        }
    });

    // Consulta de recomendações
    $('#btnConsultar').click(function() {
        const respostas = {
            refeicoes: $('input[name="refeicoes"]:checked').val(),
            frutas: $('input[name="frutas"]:checked').val(),
            exercicios: $('input[name="exercicios"]:checked').val(),
            sono: $('input[name="sono"]:checked').val()
        };

        if (!Object.values(respostas).every(Boolean)) {
            alert('Por favor, responda todas as perguntas antes de consultar.');
            return;
        }

        const conselhos = gerarRecomendacoes(respostas);
        $('#resultado').html('<p>' + conselhos.replace(/\n\n/g, '</p><p>') + '</p>');
    });
});

// Função separada para gerar recomendações
function gerarRecomendacoes({refeicoes, frutas, exercicios, sono}) {
    let conselhos = "";
    
    // Analisando o número de refeições
       switch (refeicoes) {
        case "5 ou mais":
            conselhos += "Você está consumindo muitas refeições ao longo do dia. Tente não exagerar na quantidade para evitar sobrecarga calórica.\n\n";
            break;
        case "4":
            conselhos += "Quatro refeições diárias são razoáveis, mas tenha certeza de que elas são equilibradas e saudáveis.\n\n";
            break;
        case "3":
            conselhos += "Você está fazendo três refeições diárias, o que é geralmente recomendado. Lembre-se de manter um equilíbrio entre os grupos alimentares.\n\n";
            break;
        case "2":
            conselhos += "Você está fazendo poucas refeições. O ideal é fazer de 3 a 5 refeições diárias, distribuindo-as ao longo do dia para manter o metabolismo ativo.\n\n";
            break;
        case "1":
            conselhos += "Você está fazendo uma refeição por dia, o que pode não ser suficiente. Considere aumentar o número de refeições para melhorar sua nutrição e energia.\n\n";
            break;
    }

    // Analisando o consumo de frutas
    switch (frutas) {
        case "Todos os dias":
            conselhos += "Ótimo! Você está consumindo frutas todos os dias, o que é excelente para a sua saúde.\n\n";
            break;
        case "Frequentemente":
            conselhos += "Bom trabalho! Tente garantir que você está consumindo uma variedade de frutas para obter diferentes nutrientes.\n\n";
            break;
        case "Consumo pouco":
            conselhos += "Você consome frutas pouco. Tente aumentar a ingestão de frutas para melhorar sua ingestão de vitaminas e fibras.\n\n";
            break;
        case "Não consumo":
            conselhos += "É importante incluir frutas na sua dieta para obter as vitaminas e fibras necessárias. Considere adicionar frutas à sua alimentação.\n\n";
            break;
    }

    // Analisando a frequência de exercícios físicos
    switch (exercicios) {
        case "Muito ativo":
            conselhos += "Excelente! Você é muito ativo fisicamente, o que é ótimo para a sua saúde. Continue assim e não se esqueça de equilibrar com uma dieta adequada.\n\n";
            break;
        case "Ativo":
            conselhos += "Bom trabalho! Você é ativo, mas tente manter uma rotina regular de exercícios para benefícios contínuos.\n\n";
            break;
        case "Moderadamente ativo":
            conselhos += "Você é moderadamente ativo. Considere aumentar a frequência ou a intensidade dos seus exercícios para melhorar ainda mais sua saúde.\n\n";
            break;
        case "Sedentário":
            conselhos += "Você está muito sedentário. Tente incluir atividades físicas regulares na sua rotina para melhorar sua saúde geral.\n\n";
            break;
    }

    // Analisando a qualidade do sono
    switch (sono) {
        case "Muito boa":
            conselhos += "Ótimo! Sua qualidade de sono é excelente. Continue mantendo esses hábitos para um bom descanso.\n\n";
            break;
        case "Boa":
            conselhos += "Sua qualidade de sono é boa. Se possível, tente melhorar ainda mais para maximizar seus benefícios.\n\n";
            break;
        case "Ruim":
            conselhos += "Sua qualidade de sono não está ideal. Tente melhorar seus hábitos de sono para ter mais disposição durante o dia.\n\n";
            break;
        case "Muito ruim":
            conselhos += "Sua qualidade de sono é muito ruim. Considere adotar práticas para melhorar seu sono, como manter uma rotina de sono consistente e evitar estimulantes antes de dormir.\n\n";
            break;
    }

    // Adicionar recomendações gerais
    conselhos += "Recomendações gerais:\n";
    conselhos += "- Mantenha uma alimentação balanceada com proteínas, carboidratos e gorduras saudáveis\n";
    conselhos += "- Beba pelo menos 2 litros de água por dia\n";
    conselhos += "- Tente dormir entre 7-9 horas por noite\n";
    conselhos += "- Faça pausas regulares se trabalhar sentado por longos períodos";

    return conselhos;
}
