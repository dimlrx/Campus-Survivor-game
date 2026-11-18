package nl.saxion.game.campussurvivor.npc;
import nl.saxion.game.campussurvivor.systems.StudentStats;
import nl.saxion.game.campussurvivor.systems.GameClock;
import nl.saxion.game.campussurvivor.gameplay.MissionSystem;
/** Branching, multi-step student conversations, with consequences. */
public final class DialogueSystem {
 private final StudentStats stats;private final MissionSystem missions;
 private String person="";
 private static final String[][] EXTRA = {
  {"Mila","Our group project deadline is coming up and nobody has made a plan.","I want to contribute, but I also have a part-time job.","Let's split the project into smaller tasks.","I can review your part after class.","Ask the team to meet at Saxion."},
  {"Jesse","I have a long shift at Jumbo tomorrow.","It pays the rent, but my energy is running low.","Plan your shifts around lectures.","Ask a colleague to swap shifts.","Take a proper break tonight."},
  {"Lotte","I am trying to save for a new bicycle.","Every time I save something, another unexpected bill arrives.","Let's make a weekly budget.","Maybe look for a student discount.","I can help you compare second-hand bikes."},
  {"Daan","The programming exam is getting closer.","I know the theory, but I freeze when I see unfamiliar code.","Let's practice Java exercises together.","Try explaining the code aloud.","Ask the lecturer for a practice test."},
  {"Yara","Everyone is meeting at the Oude Markt tonight.","I want to go, but I have an early lecture tomorrow.","Go for a short visit and head home early.","Skip tonight and meet them this weekend.","Suggest a non-alcoholic drink and a chat."},
  {"Bram","My Kennispark prototype works, but the demo keeps crashing.","The presentation is tomorrow and I need another pair of eyes.","Let's reproduce the bug step by step.","Check the logs and test one change at a time.","Ask a classmate to test the demo."},
  {"Sophie","My student flat is too noisy to study.","I keep losing focus, even when I have enough time.","Let's find a quiet spot at Saxion.","Try a short study session with breaks.","Talk to your flatmates about quiet hours."},
  {"Rayan","I just moved into the student flat.","I still need groceries and I don't know anyone here.","I'll show you where Jumbo is.","Come meet some classmates at Saxion.","Let's explore the centre together."}
 };private int stage=0;private String response="";private boolean active=false;
 public DialogueSystem(StudentStats stats,MissionSystem missions){this.stats=stats;this.missions=missions;}
 public boolean active(){return active;}
 public void close(){active=false;}
 public void start(String name,GameClock clock){person=name;stage=0;active=true;response="";}
 public String[] lines(){
  if(stage==0){
   if(person.equals("Tim"))return new String[]{"TIM: We have a presentation on Friday and half the slides are empty.","I stayed up late fixing the code. Can you help us plan?","[1] Let's divide the work fairly.","[2] I'll finish the slides tonight.","[3] Maybe we should ask the teacher for advice.","[Q] Leave conversation"};
   if(person.equals("Noor"))return new String[]{"NOOR: I understand the lectures, but the exam questions scare me.","My notes are a mess and everyone else seems prepared.","[1] Let's make a study schedule together.","[2] I can explain the difficult chapter.","[3] Take a break; you look exhausted.","[Q] Leave conversation"};
   if(person.equals("Sam"))return new String[]{"SAM: The Oude Markt is busy tonight, but I have an early class.","My friends want me to come. I'm not sure it's worth it.","[1] Come for one drink and leave early.","[2] Stay home and protect tomorrow's energy.","[3] Invite everyone for coffee instead.","[Q] Leave conversation"};
   if(person.equals("Alex"))return new String[]{"ALEX: My laptop died just before the deadline.","I have backups, but I can't submit without a working machine.","[1] Let's find a computer at Saxion.","[2] I can lend you some money for repairs.","[3] Let's contact the IT helpdesk.","[Q] Leave conversation"};
   for(String[] npc:EXTRA)if(person.equals(npc[0]))return new String[]{person.toUpperCase()+": "+npc[1],npc[2],"[1] "+npc[3],"[2] "+npc[4],"[3] "+npc[5],"[Q] Leave conversation"};
   return new String[]{person+": Hi!","How is your semester going?","[1] Let's study together.","[2] Good luck with your classes.","[3] See you around.","[Q] Leave conversation"};
  }
  if(stage==1)return new String[]{person.toUpperCase()+": "+response,"That actually helps. Student life is easier when we cooperate.","What should we do next?","[1] Make a concrete plan and follow up tomorrow.","[2] Tell the rest of the group what we decided.","[3] Wish them luck and head back to campus.","[Q] Leave conversation"};
  return new String[]{person.toUpperCase()+": Thanks for taking the time to talk.","I will remember that you listened instead of rushing away.","Maybe we'll run into each other after the next lecture.","[1/2/3] End conversation  |  [Q] Leave"};
 }
 public void choose(int option){if(!active)return;
  if(stage==0){
   if(person.equals("Tim")){response=option==1?"Fair tasks and clear deadlines would fix a lot.":option==2?"Thanks, but please don't burn yourself out.":"Good idea; we should ask before it's too late.";stats.stress+=option==2?6:-3;}
   else if(person.equals("Noor")){response=option==1?"A realistic schedule sounds much better.":option==2?"Your explanation makes the topic less scary.":"You're right. A short break might help me focus.";stats.knowledge+=option==2?6:2;}
   else if(person.equals("Sam")){response=option==1?"One drink sounds manageable if I keep track of time.":option==2?"Tomorrow's lecture is probably more important.":"Coffee is a brilliant compromise.";stats.alcohol+=option==1?6:0;stats.popularity+=3;}
   else if(person.equals("Alex")){response=option==1?"The library PCs might save my deadline.":option==2?"That's generous. I will pay you back.":"IT support might recover my files.";if(option==2 && stats.money>=10){stats.money-=10;stats.popularity+=8;}}
   else {response=option==1?"That gives me a practical way to start. Thanks!":option==2?"Good point. I hadn't thought of that.":"I appreciate you making time to help.";
    stats.popularity+=option==3?4:2;
    if(person.equals("Daan")||person.equals("Bram")||person.equals("Mila"))stats.knowledge+=option==1?4:1;
    if(person.equals("Yara")||person.equals("Jesse")||person.equals("Sophie"))stats.stress-=option==2?4:2;
   }
   stats.normalize();stage=1;
  }else if(stage==1){missions.npcReward();stage=2;}else close();
 }
}
