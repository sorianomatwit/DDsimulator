NOTE: this simulator print to console if you cannot print to console it does generate a spreadsheet for every simulation you run
to change parameters of the simulator go to line 105 in Main.Java
if you want to test a babies 
first need to add the ability to the simulation by creating a varibale in main
Class	Name (copy format when using it in unity) Team affected, target position, ability trigger, target amt then the pararmeters of the ability 	 
Ability DamageEBLM1 = new DmgAbility(sim, TeamAffected.Enemy, TargetPosition.BackOfLineup, AbilityTrigger.PreMatch, 1, 1); 
to add a baby must hgave an ability to be a baby if not it will be seen as a summon
allBabies.add(new Demigod("Aphrodite", 1, 1, BuffPSA21));
