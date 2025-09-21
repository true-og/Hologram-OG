# Hologram-OG
A soft fork of Lightweight Holograms for 1.19.4, a hologram plugin for paper servers. It is designed to use as little resources as possible, in the smallest jar possible!

How to use:

To begin, simple drag the .jar file from the latest release into your PaperMC Minecraft server, then restart it!

The command list is this:

`/holo <operation>`

There are five operations, `create`, `edittext`, `movehere`, `delete`, `addline`, and `list`.

After you enter the operation, there are a few more steps depending on the operation you selected.

For `create`, all you need to do is type the text in the hologram. Normally, this must be done in [MiniMessage format](https://docs.advntr.dev/minimessage/format.html), but you can use `--nominimessage` at the end to use legacy codes (&c, &7, etc)

For `edittext`, you must first enter the id of the hologram. You can find this by running the `list` operation. After the id, you put the text you want to replace it with, following the same format as `create`.

For `movehere`, you only need to enter the id of the hologram you wish to move, which can be found with the `list` operation.

`addLine` just adds a new line to the selected hologram. For simplicity's sake, all this does is create a new hologram underneath the existing one with the given text. One quirk with this is that when the server is restarted, you have to add lines to the bottom line in the chain, the top one will overlap. To delete a line, just delete the hologram. It will show up in the `holo list` operation.

Finally, `delete`, you also only need to enter the id of the hologram, which, once again, can be found with `list`.

Now you're a pro at Hologram-OG! Have fun!

(More features may be added in the future)
