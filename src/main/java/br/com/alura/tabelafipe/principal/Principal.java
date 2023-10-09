package br.com.alura.tabelafipe.principal;

import br.com.alura.tabelafipe.model.DadosMarca;
import br.com.alura.tabelafipe.model.DadosModelo;
import br.com.alura.tabelafipe.model.Veiculos;
import br.com.alura.tabelafipe.service.ConsumoApi;
import br.com.alura.tabelafipe.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumoApi = new ConsumoApi();

    private ConverteDados conversor = new ConverteDados();

    private final String URL ="https://parallelum.com.br/fipe/api/v1/";


    public void exibeMenu() {
        System.out.println("""
        Qual tipo de veículo deseja buscar? 
        Carros 
        Caminhoes 
        Motos
        """);

        String endereco;

        var tipoVeiculo = leitura.nextLine().toLowerCase();

         endereco = URL+ tipoVeiculo + "/marcas" ;

        var json = consumoApi.obterDados(endereco);


        System.out.println(json);

       var marcas = conversor.obterLista(json, DadosMarca.class);

       marcas.stream()
               .sorted(Comparator.comparing(DadosMarca::codigo))
               .forEach(System.out::println);

        System.out.println("Digite o código da marca desejada: ");
        var codigoMarca = leitura.nextLine();
        endereco += "/" + codigoMarca + "/modelos";

        json = consumoApi.obterDados(endereco);

        System.out.println(json);

        var modeloLista = conversor.obterDados(json, DadosModelo.class);

        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(m -> m.nome().toLowerCase()))
                .forEach(System.out::println);

        System.out.println("Digite o modelo do carro");

        var trechoModelo = leitura.nextLine();

        List<DadosMarca> modeloBuscado = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(trechoModelo.toLowerCase()))
                .collect(Collectors.toList());


        System.out.println("Modelos Filtrados");
        modeloBuscado.forEach(System.out::println);

        System.out.println("Digite o código do modelo desejado");
        var codigoModelo = leitura.nextLine();

        endereco += "/" + codigoModelo + "/anos";

        json = consumoApi.obterDados(endereco);

        var anoLista = conversor.obterLista(json, DadosMarca.class);
        List<Veiculos> veiculos = new ArrayList<>();

        for(var ano : anoLista){
            var enderecoAnos = endereco + "/" + ano.codigo();
            json = consumoApi.obterDados(enderecoAnos);
            var veiculo = conversor.obterDados(json, Veiculos.class);
            veiculos.add(veiculo);
        }

        System.out.println("Veículos encontrados");
        veiculos.forEach(System.out::println);


    }

}
