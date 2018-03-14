package primeiroprojeto.com.br.ipizza;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech objTextoParaVoz;

    private Button btnIniciarAtendimento;

    private static final int PARAM_RESQUISICAO_PEGAR_TEXTO_FALADO_PIZZA = 1;
    private static final int PARAM_REQUISICAO_PEGAR_TEXTO_FALADO_BEBIDA = 2;
    private static final int PARAM_REQUISICAO_PEGAR_TEXTO_FALADO_CONFIRM_PEDIDO = 3;

    private double valorPedido = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIniciarAtendimento = (Button) findViewById(R.id.btnInicar); //pegar botão interface

        objTextoParaVoz = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() { //Podemos usar somento THIS depende do contexto
            @Override
            public void onInit(int status) { //configurações iniciais de texto para voz

                objTextoParaVoz.setLanguage(new Locale("pt")); //utilizar lingua portuguesa

                objTextoParaVoz.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    @Override//onstart quando esta iniciando uma fala
                    public void onStart(String utteranceId) {

                    }

                    @Override//ondone significa que acabou de falar o smartfone
                    public void onDone(String utteranceId) {

                        iniciarCapturaFala(Integer.parseInt(utteranceId));

                    }

                    @Override//onerror quando surge um erro
                    public void onError(String utteranceId) {

                    }
                });
            }
        });

        btnIniciarAtendimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //obj recebe speak para falar, e começar fomar uma fila com queue flush
                objTextoParaVoz.speak("Bem Vindo ao iPizza. Escolha o sabor da sua Pizza.", TextToSpeech.QUEUE_FLUSH, null,
                        String.valueOf(PARAM_RESQUISICAO_PEGAR_TEXTO_FALADO_PIZZA));
            }
        });
    }

    //
    private void iniciarCapturaFala(Integer flagReconhecimento) {
        //INTENT(INTENÇAO) METODO PARA ACESSAR OUTROS APLICATIVOS E OUTRAS TELA DO SEU APLICATIVO.
        //TBM PARA O ANDROID ENTENDER A VOZ
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //putExtra passando parametro, modelo de linguagem e qual modelo
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Faça o Pedido");
        startActivityForResult(intent, flagReconhecimento);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {

        ArrayList<String> listaPossiveisFala = dataIntent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

        //Verifica se a lista está preenchida
        if (listaPossiveisFala != null && listaPossiveisFala.size() > 0) {

            boolean achou = false;

            //Percorrer a lista
            for (int i = 0; i < listaPossiveisFala.size(); i++) {

                String resposta = listaPossiveisFala.get(i);

                if (requestCode == PARAM_RESQUISICAO_PEGAR_TEXTO_FALADO_PIZZA) {

                    if (resposta.equalsIgnoreCase("Mussarela") || resposta.equalsIgnoreCase("Pizza Mussarela") ||
                            resposta.equalsIgnoreCase("Pizza de Mussarela")) {

                            achou = true;
                            valorPedido = valorPedido + 24.00;

                        objTextoParaVoz.speak("Entendi, Pizza de Mussarela. Escolha uma Bebida.", TextToSpeech.QUEUE_FLUSH, null,
                                String.valueOf(PARAM_REQUISICAO_PEGAR_TEXTO_FALADO_BEBIDA));
                    }

                    if (resposta.equalsIgnoreCase("Calabresa") || resposta.equalsIgnoreCase("Pizza Calabresa") ||
                            resposta.equalsIgnoreCase("Pizza de Calabresa")) {

                            achou = true;
                            valorPedido = valorPedido + 24.00;

                        objTextoParaVoz.speak("Entendi, Pizza de Calabresa. Escolha uma Bebida.", TextToSpeech.QUEUE_FLUSH, null,
                                String.valueOf(PARAM_REQUISICAO_PEGAR_TEXTO_FALADO_BEBIDA));
                    }

                    if (resposta.equalsIgnoreCase("Portuguesa") || resposta.equalsIgnoreCase("Pizza Portuguesa") ||
                            resposta.equalsIgnoreCase("Pizza de Portuguesa")) {

                            achou = true;
                            valorPedido = valorPedido + 26.00;

                        objTextoParaVoz.speak("Entendi, Pizza de Portuguesa. Escolha uma Bebida.", TextToSpeech.QUEUE_FLUSH, null,
                                String.valueOf(PARAM_REQUISICAO_PEGAR_TEXTO_FALADO_BEBIDA));
                    }

                } else if (requestCode == PARAM_REQUISICAO_PEGAR_TEXTO_FALADO_BEBIDA) {


                    if (resposta.equalsIgnoreCase("Coca") || resposta.equalsIgnoreCase("CocaCola") ||
                            resposta.equalsIgnoreCase("coquinha")) {

                            achou = true;
                            valorPedido = valorPedido + 8.50;

                        objTextoParaVoz.speak("Entendi, Coca Cola. O valor do Pedido é:"+ String.valueOf(valorPedido)+". Confirma o seu pedido?", TextToSpeech.QUEUE_FLUSH, null,
                                String.valueOf(PARAM_REQUISICAO_PEGAR_TEXTO_FALADO_CONFIRM_PEDIDO));
                    }

                    if (resposta.equalsIgnoreCase("Kuat") || resposta.equalsIgnoreCase("quati") ||
                            resposta.equalsIgnoreCase("cuati")) {

                            achou = true;
                            valorPedido = valorPedido + 6.00;

                        objTextoParaVoz.speak("Entendi, Kuat. O valor do Pedido é:"+ String.valueOf(valorPedido)+". Confirma o seu pedido?", TextToSpeech.QUEUE_FLUSH, null,
                                String.valueOf(PARAM_REQUISICAO_PEGAR_TEXTO_FALADO_CONFIRM_PEDIDO));
                    }

                    if (resposta.equalsIgnoreCase("Sprit") || resposta.equalsIgnoreCase("limão") ||
                            resposta.equalsIgnoreCase("ispraite")) {

                            achou = true;
                            valorPedido = valorPedido + 4.00;
                        objTextoParaVoz.speak("Entendi, Sprit. O valor do Pedido é:"+ String.valueOf(valorPedido)+". Confirma o seu pedido?", TextToSpeech.QUEUE_FLUSH, null,
                                String.valueOf(PARAM_REQUISICAO_PEGAR_TEXTO_FALADO_CONFIRM_PEDIDO));

                    } else if (requestCode == PARAM_REQUISICAO_PEGAR_TEXTO_FALADO_CONFIRM_PEDIDO) {

                        if (resposta.equalsIgnoreCase("Sim") || resposta.equalsIgnoreCase("Ok") || resposta.equalsIgnoreCase("Confirmar")) {

                            achou = true;

                            objTextoParaVoz.speak("Ok, Pedido Confirmado. Em 45 minutos seu pedido será entregue!" +
                                    "iPizza agradece a preferencia!", TextToSpeech.QUEUE_FLUSH, null, null);
                        }

                        if (resposta.equalsIgnoreCase("Não") || resposta.equalsIgnoreCase("Not") || resposta.equalsIgnoreCase("Cancelar")) {

                            achou = true;

                            objTextoParaVoz.speak("Ok, Pedido Cancelado, Faça seu pedido novamente." +
                                    "iPizza agradece a preferencia!", TextToSpeech.QUEUE_FLUSH, null, null);
                        }

                        valorPedido = 0.0;
                    }

                    if(!achou){

                        valorPedido = 0.0;
                        Toast.makeText(this, "Não entendi.", Toast.LENGTH_LONG).show();

                    }

                }
            }
        }
    }
}